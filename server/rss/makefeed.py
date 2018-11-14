import datetime

from urllib.parse import parse_qs

import dateutil.parser

from rfeed import *

import sqlite3
import calendar
import time

DB_FILE="/tmp/caladrius.db"
conn = sqlite3.connect(DB_FILE)

def makefeed(id):
    item0 = Item(
        title = "Now is " + datetime.datetime.now().isoformat(),
        link = "https://caladrius.ivanjohnson.net",
        description = "foo",
        author = "email@example.com (Ivan Johnson)",
        guid = Guid("oiwefkcxvmwojwefhdlkj", isPermaLink=False),
        pubDate = datetime.datetime.now()
    )

    item1 = Item(
        title = "Caladrius",
        link = "https://caladrius.ivanjohnson.net",
        description = "This is Caladrius' internal homepage",
        creator = "Ivan Johnson",
        guid = Guid("https://caladrius.ivanjohnson.net", isPermaLink=True),
        pubDate = datetime.datetime(2018, 9, 18, 0, 0)
    )

    item2 = Item(
        title = "GitHub",
        link = "https://github.ivanjohnson.net/caladrius",
        description = "This is Caladrius' source code",
        creator = "Caladrius",
        guid = Guid("https://github.ivanjohnson.net/caladrius", isPermaLink=True),
        pubDate = datetime.datetime(2018, 9, 17, 0, 0)
    )

    feed = Feed(
        title = "This is feed of id "+str(id),
        link = "https://caladrius.ivanjohnson.net/",
        description = "This is a RSS feed of Caladrius related stuff",
        language = "en-US",
        lastBuildDate = datetime.datetime.now(),
        items = [item0, item1, item2]
    )
    return feed.rss()

def badRequest(start_response, msg="400 Bad Request (no error message was set)"):
        start_response('400 Bad Request', [('Content-type', 'text/plain')])
        return [msg.encode('utf8')]

def handleFeed(environ, start_response):
        print("THIS IS A DEBUG MESSAGE")
        try:
                uuid = environ['HTTP_USERUUID']
        except KeyError:
                return badRequest(start_response, "You gotta provide the useruuid header\n")

        try:
                qs = environ['QUERY_STRING']
                qs = parse_qs(qs)
                feedid = int(qs['id'][-1])
        except Exception:
                return badRequest(start_response, "Query string must set the id to an integer\n")

        if (environ['REQUEST_METHOD'] == "GET"):
                with conn:
                        c = conn.cursor()
                        c.execute('SELECT feedbase64,lastModify FROM feeds WHERE userid=? AND feedid=?', (uuid, feedid))
                        (feedbase64,lastModify) = c.fetchone()

                start_response('200 OK', [('Content-Type', 'text/plain')])
                st = f"base64: {feedbase64}, lastModify: {lastModify}\n"

                return [(st).encode('utf8')]
        elif (environ['REQUEST_METHOD'] == "PUT"):
                try:
                        moddate = environ['HTTP_MODDATE']
                        moddate = int(moddate)
                        moddate = datetime.datetime.fromtimestamp(moddate)
                except Exception:
                        return badRequest(start_response, "You gotta set the moddate header to a unix timestamp less than now\n")

                #TODO assert moddate < now
                #TODO assert moddate >= actual mod date

                curtime=calendar.timegm(time.gmtime())

                try:
                        length = int(environ.get('CONTENT_LENGTH', '0'))
                except ValueError:
                        length = 0
                feedbase64 = environ['wsgi.input'].read(length).decode("utf-8")

                with conn:
                        conn.execute('INSERT OR REPLACE INTO feeds(userid, feedid,      feedbase64, lastFetch, lastModify) VALUES(?,?,?,?,?)',
                                                                  (uuid,   str(feedid), feedbase64, 0,         curtime))

                start_response('200 OK', [('Content-Type', 'text/plain')])
                return ["Success, AFAIK\n".encode('utf8')]
        else:
                return badRequest(start_response, "Only gets and puts are supported here\n")

def handleFeeds(environ, start_response):
        try:
                uuid = environ['HTTP_USERUUID']
        except KeyError:
                return badRequest(start_response, "You gotta provide a useruuid header\n")

        if (environ['REQUEST_METHOD'] != "GET"):
                return badRequest(start_response, "You can only use GET here\n")

        c = conn.cursor()
        rows = c.execute('SELECT feedid FROM feeds WHERE userid=?', (uuid,))
        start_response('200 OK', [('Content-Type', 'text/plain')])
        st = ""
        for row in rows:
                st += str(row[0]) + "\n"

        start_response('200 OK', [('Content-Type', 'text/plain')])
        return [(st).encode('utf8')]



def handleRSS(environ, start_response):
        qs = environ['QUERY_STRING']
        qs = parse_qs(qs)
        try:
                id = int(qs['id'][-1])
        except KeyError:
                return badRequest(start_response, "Query string must set the id to an integer\n")

        start_response('200 OK', [('Content-Type', 'application/rss+xml')])
        return [makefeed(id).encode('utf8')]

def application(environ, start_response):
        if (environ['PATH_INFO'] == '/webapi/config/feeds'):
                return handleFeeds(environ, start_response)
        elif (environ['PATH_INFO'] == '/webapi/config/feed'):
                return handleFeed(environ, start_response)
        elif (environ['PATH_INFO'] == '/webapi/feed'):
                return handleRSS(environ, start_response)

        start_response("404 Not Found", [('Content-type', 'text/plain')])
        return [("Sorry, but we couldn't find the page: " + environ['PATH_INFO'] + '\n').encode('utf8')]
        #start_response('200 OK', [('Content-Type', 'text/plain')])
        #return [(str(environ)+"\n").encode('utf8')]

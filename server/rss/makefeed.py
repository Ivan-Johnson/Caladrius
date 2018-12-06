import datetime

from urllib.parse import parse_qs

import dateutil.parser

from rfeed import *
from subprocess import check_output

import sqlite3
import calendar
import time
import os
import sys

DB_FILE="/srv/caladrius.db"
conn = sqlite3.connect(DB_FILE)

def makefeed(feedid):
    with conn:
        c = conn.cursor()
        c.execute('SELECT userid,feedbase64 FROM feeds WHERE feedid=?', (feedid,))
        val = c.fetchone()
        if val is None:
                return None
        (userid,feedbase64) = val

        c.execute('SELECT userBase64 FROM users WHERE userid=?', (userid,))
        val = c.fetchone()
        if val is None:
                raise Exception("Getting feed of non-existant user")
        (userbase64,) = val

    items = []

    text = check_output(["java", "-jar", "/caladrius/server/rss/eventFinder.jar", str(userbase64), str(feedbase64)], stderr=sys.stderr).decode("utf-8")
    lines = text.splitlines()
    title = lines[0]
    for event in lines[1:]:
            index = event.index(' ')
            date = event[:index]
            message = event[index+1:]

            dateComponents = date.split("-")
            dateComponents = list(map(int, dateComponents))
            year = dateComponents[0]
            month = dateComponents[1]
            day = dateComponents[2]
            items.append(Item(
                    title = message,
                    link = "",
                    description = "",
                    pubDate = datetime.datetime(year, month, day, 0, 0),
                    #author = "email@example.com (Ivan Johnson)",
                    #guid = Guid("oiwefkcxvmwojwefhdlkj", isPermaLink=False),
            ))

    feed = Feed(
        title = title,
        link = "https://caladrius.ivanjohnson.net/",
        description = "",
        language = "en-US",
        lastBuildDate = datetime.datetime.now(),
        items = items
    )
    return feed.rss()

def badRequest(start_response, msg="no error message was set"):
        msg_full = '400 Bad Request ('+msg+')'
        start_response(msg_full, [('Content-type', 'text/plain')])
        return [msg_full.encode('utf8')]

def handleFeed(environ, start_response):
        print("THIS IS A DEBUG MESSAGE")
        try:
                uuid = environ['HTTP_USERUUID']
        except KeyError:
                return badRequest(start_response, "You gotta provide the useruuid header\n")

        try:
                qs = environ['QUERY_STRING']
                qs = parse_qs(qs)
                feedid = qs['id'][-1]
        except Exception:
                return badRequest(start_response, "Query string must set an id\n")

        if (environ['REQUEST_METHOD'] == "GET"):
                with conn:
                        c = conn.cursor()
                        c.execute('SELECT feedbase64,lastModify FROM feeds WHERE userid=? AND feedid=?', (uuid, feedid))
                        (feedbase64,lastModify) = c.fetchone()

                start_response('200 OK', [('Content-Type', 'text/plain')])
                st = f"base64: {feedbase64}, lastModify: {lastModify}\n"

                return [(st).encode('utf8')]
        elif (environ['REQUEST_METHOD'] == "DELETE"):
                with conn:
                        c = conn.cursor()
                        c.execute('DELETE FROM feeds WHERE userid=? AND feedid=?', (uuid, feedid))

                start_response('200 OK', [('Content-Type', 'text/plain')])
                return [("").encode('utf8')]
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

def handleUser(environ, start_response):
        try:
                uuid = environ['HTTP_USERUUID']
        except KeyError:
                return badRequest(start_response, "You gotta provide the useruuid header\n")

        if (environ['REQUEST_METHOD'] == "GET"):
                with conn:
                        c = conn.cursor()
                        c.execute('SELECT userBase64 FROM users WHERE userid=?', (uuid,))
                        (feedbase64,) = c.fetchone()

                start_response('200 OK', [('Content-Type', 'text/plain')])
                st = f"{feedbase64}\n"

                return [(st).encode('utf8')]
        elif (environ['REQUEST_METHOD'] == "PUT"):
                try:
                        length = int(environ.get('CONTENT_LENGTH', '0'))
                except ValueError:
                        length = 0
                userBase64 = environ['wsgi.input'].read(length).decode("utf-8")

                with conn:
                        conn.execute('INSERT OR REPLACE INTO users(userid, userBase64)      VALUES(?,?)',
                                                                  (uuid,   userBase64))

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
                id = qs['id'][-1]
        except KeyError:
                return badRequest(start_response, "Query string must set the id to an integer\n")

        feed = makefeed(id)
        if feed is None:
                start_response("404 Not Found", [('Content-type', 'text/plain')])
                return [("Sorry, but the specified feed does not exist\n").encode('utf8')]

        start_response('200 OK', [('Content-Type', 'application/rss+xml')])
        return [feed.encode('utf8')]

def application(environ, start_response):
        try:
                if (environ['PATH_INFO'] == '/webapi/config/feeds'):
                        return handleFeeds(environ, start_response)
                elif (environ['PATH_INFO'] == '/webapi/config/feed'):
                        return handleFeed(environ, start_response)
                elif (environ['PATH_INFO'] == '/webapi/config/user'):
                        return handleUser(environ, start_response)
                elif (environ['PATH_INFO'] == '/webapi/feed'):
                        return handleRSS(environ, start_response)

                #TODOl8r escape value of path_info
                start_response("404 Not Found", [('Content-type', 'text/plain')])
                return [("Sorry, but we couldn't find the page: " + environ['PATH_INFO'] + '\n').encode('utf8')]
        except:
                start_response("500 Internal Server Error", [('Content-type', 'text/plain')])
                import traceback
                #TODOl8r does displaying the stack trace make it easier for attackers? Is it worth the risk?
                return [(traceback.format_exc()).encode('utf8')]

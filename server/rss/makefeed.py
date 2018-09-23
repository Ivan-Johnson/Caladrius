import datetime
from rfeed import *

def makefeed():
    item0 = Item(
        title = "Now is is " + datetime.datetime.now().isoformat(),
        link = "https://caladrius.ivanjohnson.net",
        description = "foo",
        author = "Ivan Johnson",
        guid = Guid("oiwefkcxvmwojwefhdlkj", isPermaLink=False),
        pubDate = datetime.datetime.now()
    )

    item1 = Item(
        title = "Caladrius",
        link = "https://caladrius.ivanjohnson.net",
        description = "This is Caladrius' internal homepage",
        author = "Ivan Johnson",
        guid = Guid("https://caladrius.ivanjohnson.net", isPermaLink=True),
        pubDate = datetime.datetime(2018, 9, 18, 0, 0)
    )

    item2 = Item(
        title = "GitHub",
        link = "https://github.ivanjohnson.net/caladrius",
        description = "This is Caladrius' source code",
        author = "Caladrius",
        guid = Guid("https://github.ivanjohnson.net/caladrius", isPermaLink=True),
        pubDate = datetime.datetime(2018, 9, 17, 0, 0)
    )

    feed = Feed(
        title = "Caladrius",
        link = "https://caladrius.ivanjohnson.net/",
        description = "This is a RSS feed of Caladrius related stuff",
        language = "en-US",
        lastBuildDate = datetime.datetime.now(),
        items = [item0, item1, item2]
    )
    return feed.rss()

def application(environ, start_response):
    start_response('200 OK', [('Content-Type', 'application/rss+xml')])

    return [makefeed().encode('utf8')]

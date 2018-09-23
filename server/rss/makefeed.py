#slight tweaks on demo from rfeed's README
import datetime
from rfeed import *

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
    items = [item1, item2]
)

print(feed.rss())

#!/bin/bash
set -euo pipefail

echo "Compiling python"
python -m py_compile /caladrius/server/rss/makefeed.py
echo "Reloading daemons"
sudo systemctl daemon-reload
systemctl --user daemon-reload
echo "Restarting uwsgi"
sudo systemctl restart rssfeed_uwsgi

#get feed:
wget --no-cache --content-on-error \
	     --save-headers "--header=useruuid: thisisauserid1" "--header=moddate: 1540814047" \
	     -O /tmp/foo.txt "https://caladrius.ivanjohnson.net/webapi/config/feed?id=1001" || true

wget --no-cache --content-on-error --save-headers "--header=useruuid: \
     thisisauserid1" "--header=moddate: 1540814047" -O /tmp/foo.txt --method=PUT \
     --body-data="foo bar" \
     "https://caladrius.ivanjohnson.net/webapi/config/feed?id=1001" || true

less /tmp/foo.txt

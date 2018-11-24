#!/bin/bash

wget --no-cache --content-on-error --save-headers "--header=useruuid: \
     thisisauserid1" "--header=moddate: 1540814047" -O /tmp/foo.txt --method=PUT \
     --body-data="$(cat | base64)" \
     "https://caladrius.ivanjohnson.net/webapi/config/feed?id=1001" || true

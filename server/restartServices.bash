#!/bin/env bash
#exit on Error (even within a pipeline) and treat Unset variables as errors
set -euo pipefail

sudo systemctl daemon-reload

sudo systemctl restart nftables
sudo systemctl restart rssfeed_uwsgi
sudo systemctl restart nginx

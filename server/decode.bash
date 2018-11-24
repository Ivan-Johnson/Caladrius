#!/bin/bash
cat | tail -n 1 | cut -d ' ' -f 2 | cut -d ',' -f 1 | base64 -d

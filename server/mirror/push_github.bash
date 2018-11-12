#!/bin/bash
pushd /caladrius2
git fetch -p origin
git push --mirror
popd

#!/bin/bash
pushd /CaladriusMirror/
git fetch -p origin
git push --mirror
popd

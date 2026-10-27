#!/bin/bash

# Wysłanie tag'a do github'a
# git push origin <tag_name>

export RELEASE_VERSION=`cat major_version.txt`
export MINOR_VERSION=$RELEASE_VERSION.$1

echo "$MINOR_VERSION" > minor_version.txt


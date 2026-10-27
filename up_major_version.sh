#!/bin/bash

# Wysłanie tag'a do github'a
# git push origin <tag_name>

# Zmień wersje i uruchom skrypt. 
export RELEASE_VERSION=2.0
echo "$RELEASE_VERSION" > major_version.txt
echo "$RELEASE_VERSION.0.1" > minor_version.txt

mvn versions:set -DnewVersion=$RELEASE_VERSION-SNAPSHOT


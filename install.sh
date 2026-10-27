#!/bin/bash

export LIB_VERSION=`cat minor_version.txt`
export MVN_EXEC="mvn"

# nazwa profilu użytkownika
#export MAVEN_PROFILE=$1
#export MVN_EXEC="mvn --global-settings \"repo/settings.xml\" --settings \"maven/$MAVEN_PROFILE/settings.xml\""

installLibs()
{
	$MVN_EXEC versions:set -DnewVersion=$LIB_VERSION
	# tworzę domyślny pakiet: "POSTGRESQL"
	$MVN_EXEC clean install -Dmaven.test.skip
	$MVN_EXEC versions:revert
}

installLibs
exit 0
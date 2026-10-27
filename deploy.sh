#!/bin/bash

export LIB_VERSION=`cat minor_version.txt`
export MVN_EXEC="mvn"
export REPOSITORY_LOCAL="D:/workspace/.m2/repository"
export REPOSITORY_URL=https://nexusrepo.scisoftware.pl/repository/sci-maven-releases
export REPOSITORY_ID=sci-maven-releases

deployLibs()
{
  # Tworzymy tymczasowy katalog target, jeśli nie istnieje
  mkdir -p target

  # Ścieżki źródłowe (Cygwin dobrze radzi sobie ze ścieżkami Windowsowymi, ale przekształćmy je na bezpieczne)
  SRC_JAR="${REPOSITORY_LOCAL}/pl/slawas/ccrto-openapi/$LIB_VERSION/ccrto-openapi-$LIB_VERSION.jar"
  SRC_POM="${REPOSITORY_LOCAL}/pl/slawas/ccrto-openapi/$LIB_VERSION/ccrto-openapi-$LIB_VERSION.pom"

  # Kopiujemy do folderu roboczego poza repozytorium lokalnym
  cp "$SRC_JAR" target/
  cp "$SRC_POM" target/

  # mercury-lib (wysyłamy z katalogu target)
  $MVN_EXEC deploy:deploy-file \
   -Durl=${REPOSITORY_URL} \
   -DrepositoryId=${REPOSITORY_ID} \
   -Dfile=target/ccrto-openapi-$LIB_VERSION.jar \
   -DpomFile=target/ccrto-openapi-$LIB_VERSION.pom
}

deployLibs
exit 0
#!/usr/bin/env bash

echo "./docker-build.sh '-D skip.surefire.tests=true' to skip the tests"
if mvn -f ../pom.xml clean package $1; then
  rm -rf ./target
  mv ../target .
  docker build --build-arg JAR_FILE="./target/co2-backend-0.0.1-SNAPSHOT.jar" -t rimvanvliet/co2-backend:latest .
else
  echo mvn clean package FAILED!!!
fi

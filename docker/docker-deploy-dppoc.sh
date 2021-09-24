#!/usr/bin/env bash

echo "./docker-deploy.sh <patch#>"
echo "oc login required!"

docker tag triventoit-docker.jfrog.io/dppoc-dashboard:latest triventoit-docker.jfrog.io/dppoc-dashboard:0.0.$1
docker push triventoit-docker.jfrog.io/dppoc-dashboard:0.0.$1

oc tag triventoit-docker.jfrog.io/dppoc-dashboard:0.0.$1 dppoc-dashboard:0.0.$1
sleep 1
oc tag dppoc-dashboard:0.0.$1 dppoc-dashboard:latest

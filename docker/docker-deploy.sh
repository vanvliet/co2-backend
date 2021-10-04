#!/usr/bin/env bash

echo "./docker-deploy.sh <patch#>"
echo "oc login required!"

docker tag triventoit-docker.jfrog.io/co2-backend:latest triventoit-docker.jfrog.io/co2-backend:0.0.$1
docker push triventoit-docker.jfrog.io/co2-backend:0.0.$1

#oc tag triventoit-docker.jfrog.io/co2-backend:0.0.$1 co2-backend:0.0.$1
#sleep 1
#oc tag co2-backend:0.0.$1 co2-backend:latest

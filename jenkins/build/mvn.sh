#!/bin/bash

echo "****************************"
echo "** Building jar ***********"
echo "***************************"

WORKSPACE=/home/jenkins/jenkins-data/jenkins_home/workspace/pipeline-challenger-microservice

docker run --rm  -v  $WORKSPACE/challenger-microservice:/app -v /root/.m2/:/root/.m2/ -w /app maven:3-alpine "$@"

#!/bin/bash

echo "****************************"
echo "** Building jar *************"
echo "***************************"

echo "TEst ls" ls
echo "ls $PWD"

WORKSPACE=/var/jenkins_home/workspace/eline_deploy-application-jenkins

docker run --rm  -v  $WORKSPACE/:/app/ -v /root/.m2/:/root/.m2/ -w /app/ maven:3-alpine "$@"

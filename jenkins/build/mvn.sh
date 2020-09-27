#!/bin/bash

echo "****************************"
echo "** Building jar *************"
echo "****************************"

WORKSPACE=/var/jenkins_home/workspace/rvice_deploy-application-jenkins

docker run --rm  -v $WORKSPACE:/app -v /root/.m2/:/root/.m2/ -w /app maven:3-alpine "$@"

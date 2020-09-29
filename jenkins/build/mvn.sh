#!/bin/bash

echo "****************************"
echo "** Building jar *************"
echo "****************************"

# WORKSPACE=/var/jenkins_home/workspace/rvice_deploy-application-jenkins

# TODO Fix mapping, the current setup use the docker volume folder
WORKSPACE=/var/lib/docker/volumes/ubuntu_jenkins-home/_data/workspace/rvice_deploy-application-jenkins

docker run --rm  -v $WORKSPACE:/app -v /root/.m2/:/root/.m2/ -w /app maven:3-alpine "$@"

# docker run --rm -it -v /root/.m2/:/root/.m2/ -v /var/jenkins_home/workspace/rvice_deploy-application-jenkins/:/app/ -w /app maven:3-alpine bash

# docker run --rm -it -v /root/.m2/:/root/.m2/ -v /var/lib/docker/volumes/ubuntu_jenkins-home/_data/workspace/rvice_deploy-application-jenkins:/app/ -w /app maven:3-alpine bash
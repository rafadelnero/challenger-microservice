#!/bin/bash

echo "****************************"
echo "** Building jar *************"
echo "****************************"

# WORKSPACE=/var/jenkins_home/workspace/rvice_deploy-application-jenkins

# TODO Fix mapping, the current setup use the docker volume folder
echo "Current workspace: "$WORKSPACE
WORKSPACE=/var/lib/docker/volumes/ubuntu_jenkins-home/_data/workspace/rvice_deploy-application-jenkins

ABSOLUTE_WORKSPACE="/var/lib/docker/volumes/ubuntu_jenkins-home/_data/workspace"
JOB_WORKSPACE="\${PWD##*/}"

docker run --rm  -v ${ABSOLUTE_WORKSPACE}/${JOB_WORKSPACE}:/app -v /root/.m2/:/root/.m2/ -w /app maven:3.6.3-jdk-11-slim "$@"

# docker run --rm -it -v /root/.m2/:/root/.m2/ -v /var/jenkins_home/workspace/rvice_deploy-application-jenkins/:/app/ -w /app maven:3-alpine bash

# docker run --rm -it -v /root/.m2/:/root/.m2/ -v /var/lib/docker/volumes/ubuntu_jenkins-home/_data/workspace/rvice_deploy-application-jenkins:/app/ -w /app maven:3-alpine bash

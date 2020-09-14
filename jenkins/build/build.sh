#!/bin/bash

# Copy the new jar to the build location
cp -f challenger-microservice/target/*.jar jenkins/build/

echo "***************************"
echo "** Building Docker Image ***"
echo "****************************"

cd jenkins/build/ && docker-compose -f docker-compose-build.yml build --no-cache

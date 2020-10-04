pipeline {

    agent any

    environment {
        ABSOLUTE_WORKSPACE = "/var/lib/docker/volumes/ubuntu_jenkins-home/_data/workspace/"
        JOB_WORKSPACE = "\${PWD##*/}"
    }

    stages {
        stage('Build Test') {
            steps {
                echo "****************************"
                echo "** Building jar ************"
                echo "****************************"
                sh "docker run --rm  -v ${ABSOLUTE_WORKSPACE}/${JOB_WORKSPACE}:/app -v /root/.m2/:/root/.m2/ -w /app maven:3.6.3-jdk-11-slim mvn clean package"

                echo $(pwd)
//                 cp -f challenger-microservice/target/*.jar jenkins/build/
//
//                 echo "****************************"
//                 echo "** Building Docker Image ***"
//                 echo "****************************"
//
//                 cd jenkins/build/ && docker-compose -f docker-compose-build.yml build --no-cache
            }

            post {
                success {
                   archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
    }

}

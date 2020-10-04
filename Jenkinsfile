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
                sh "docker run --rm  -v ${ABSOLUTE_WORKSPACE}/${JOB_WORKSPACE}:/app -v /root/.m2/:/root/.m2/ -w /app maven:3.6.3-jdk-11-slim mvn clean package -DskipTests"

                echo "${PWD}"

                echo "****************************"
                echo "** Building Docker Image ***"
                echo "****************************"

                sh "docker-compose build --no-cache"
            }

            post {
                success {
                   archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Test') {
            steps {
                 sh "docker run --rm  -v ${ABSOLUTE_WORKSPACE}/${JOB_WORKSPACE}:/app -v /root/.m2/:/root/.m2/ -w /app maven:3.6.3-jdk-11-slim mvn test"
            }

            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }

}

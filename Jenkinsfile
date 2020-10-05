pipeline {

    agent any

    environment {
        ABSOLUTE_WORKSPACE = "/var/lib/docker/volumes/ubuntu_jenkins-home/_data/workspace/"
        JOB_WORKSPACE = "\${PWD##*/}"
        PASS = credentials('docker-hub-key')
        IMAGE="maven-project"
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

        stage('Push') {
            steps {
                echo "********************"
                echo "** Pushing image ***"
                echo "********************"

                echo "** Logging in ***"
                sh "docker login -u rafadelnero -p $PASS"

                echo "$IMAGE:$BUILD_TAG"
                echo "*** Tagging image ***"
                sh "docker tag $IMAGE:$BUILD_TAG rafadelnero/$IMAGE:$BUILD_TAG"

                echo "*** Pushing image ***"
                sh "docker push rafadelnero/$IMAGE:$BUILD_TAG"
            }
        }

        stage('Deploy') {
            steps {
               echo 'Will deploy'
            }
        }
    }

}

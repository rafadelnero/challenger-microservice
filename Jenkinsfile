pipeline {
    agent { docker { image 'maven:3.6.3-jdk-11' } }

     environment {
        registry = "rafadelnero/challenger-microservice"
        registryCredential = 'dockerhub'
        dockerImage = ''
     }

    stages {
        stage('compile') {
            steps {
                echo 'Compiling project'
                sh 'mvn compile'
            }
        }
        stage('test') {
            steps {
                echo 'Running tests'
                sh 'mvn clean test'
            }
        }
        stage('build') {
            steps {
                echo 'Building project'
                sh 'mvn clean package'
            }
        }

        stage('docker build/push') {
            docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {
                def app = docker.build("rafadelnero/challenger-microservice:${commit_id}", '.').push()
            }
        }

        stage('Building our image') {
             steps {
                 script {
                     dockerImage = docker.build registry + ":$BUILD_NUMBER"
                 }
             }
        }

        stage('Deploy our image') {
             steps {
                  script {
                      docker.withRegistry( '', registryCredential ) {
                          dockerImage.push()
                      }
                  }
             }
         }

        stage('Cleaning up') {
             steps {
                 sh "docker rmi $registry:$BUILD_NUMBER"
             }
        }
    }

    post {
        always {
            sh "mvn clean"
            echo "Finished"
        }
        success {
            echo "Success"
        }
        unstable {
            echo "Unstable"
        }
        failure {
            echo "Failure"
        }
   }
}

pipeline {
    agent { docker { image 'maven:3.6.3-jdk-11' } }
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
                sh 'mvn clean test -DDATABASE_HOST=mongodb'
            }
        }
        stage('build') {
            steps {
                echo 'Building project'
                sh 'mvn clean package'
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

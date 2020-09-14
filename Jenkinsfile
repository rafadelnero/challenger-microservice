pipeline {

    agent any

    stages {
        stage('delete files from workspace') {
          steps {
            sh 'ls -l'
            sh 'sudo rm -rf ./*'
          }
        }

        stage('Build Test') {
            steps {
                sh '''
                    ./jenkins/build/mvn.sh mvn -B -DskipTests clean package
                '''
            }

            post {
                success {
                   archiveArtifacts artifacts: 'java-app/target/*.jar', fingerprint: true
                }
            }
        }
    }

}

pipeline {

    agent any

    stages {

        stage('Build') {
            steps {
                sh '''
                    ./jenkins/build/mvn.sh mvn -B -DskipTests clean package
                    ./jenkins/build/build.sh
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

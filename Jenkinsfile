pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/kevinBonifacio/Bonifacio_Kevin_LMS.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}

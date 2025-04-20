pipeline {
    agent any

    environment {
        STAGING_DIR = 'staging_build'
        PROD_DIR = 'production_build'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/your-username/your-repo.git'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean install'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Deploy to Staging') {
            steps {
                echo "Deploying to staging..."
                // Replace with your staging deployment steps
                sh 'cp -r target/* $STAGING_DIR'
            }
        }

        stage('Manual Approval') {
            steps {
                input message: "Deploy to Production?"
            }
        }

        stage('Deploy to Production') {
            steps {
                echo "Deploying to production..."
                // Replace with your production deployment steps
                sh 'cp -r target/* $PROD_DIR'
            }
        }
    }

    post {
        always {
            echo "Cleaning up..."
        }
        success {
            echo "Build completed successfully!"
        }
        failure {
            echo "Build failed. Check logs."
        }
    }
}


pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Some build steps here
                bat 'make clean'
                bat 'make'
            }
        }

        stage('Test') {
            steps {
                // Some test steps here
                bat 'make test'
            }
        }

        stage('Deploy') {
            steps {
                // Some deployment steps here
                bat 'make deploy'
            }
        }
    }

    post {
        success {
            // This block will run if the pipeline is successful
            echo 'Build successful! Yay!'
        }
        failure {
            // This block will run if the pipeline fails
            echo 'Build failed! Oh no!'
        }
    }
}

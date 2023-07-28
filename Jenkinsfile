
pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Some build steps here
                bat 'mvn clean'
               
            }
        }

        stage('Test') {
            steps {
                // Some test steps here
                bat 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                // Some deployment steps here
                bat 'mvn deploy'
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


     pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Check out the code from the GitHub repository
                git 'https://github.com/amirh3sam/LibraryAllInOne'
            }
        }

        stage('Build') {
            steps {
                // Assuming it's a Maven project, build it
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                // Assuming it's a Maven project, run tests
                sh 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                // Assuming you deploy your artifact after a successful build
                // Replace this with your actual deployment steps
                echo 'Deployment step here'
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

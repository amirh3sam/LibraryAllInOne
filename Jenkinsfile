
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
                bat 'mvn test -Dcucumber.filter.tags=@smoke'
            }
        }
    }

    post {
        always{

              cucumber( buildStatus: 'null', 
                        customCssFiles: '', 
                        customJsFiles: '', 
                        failedFeaturesNumber: -1, 
                        failedScenariosNumber: -1, 
                        failedStepsNumber: -1, 
                        fileIncludePattern: 'target/cucumber.json', 
                        pendingStepsNumber: -1, 
                        skippedStepsNumber: -1, 
                        sortingMethod: 'ALPHABETICAL', 
                        undefinedStepsNumber: -1)
        }
    }
}

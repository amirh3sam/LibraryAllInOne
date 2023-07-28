
      pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout your Git repository
             git 'https://github.com/amirh3sam/LibraryAllInOne'
                git log origin/main

            }
        }

        stage('Build and Execute Tests') {
            steps {
                script {
                  //  bat"mvn clean install"
                  bat "mvn test -Dcucumber.filter.tags=@smoke"
                }
            }
        }
    }

    post {
        always {
            // Publish Cucumber reports using the 'cucumber' step from the Cucumber Reports plugin
            cucumber(
                buildStatus: 'null',
                customCssFiles: '',
                customJsFiles: '',
                failedFeaturesNumber: -1,
                failedScenariosNumber: -1,
                failedStepsNumber: -1,
                fileIncludePattern: '*/.json',
                jsonReportDirectory: 'target',
                pendingStepsNumber: -1,
                reportTitle: 'my cucumber report',
                skippedStepsNumber: -1,
                sortingMethod: 'ALPHABETICAL',
                undefinedStepsNumber: -1
            )
        }
    }
}

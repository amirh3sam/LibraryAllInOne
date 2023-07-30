pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
     // Some build steps here
    //Clears the target directory and builds the project and packages the resulting JAR file into the target directory - without running the unit tests during the build.
               bat  'mvn clean package -Dmaven.test.skip=true'
               
            }
        }

        stage('Test') {
            steps {
                // Some test steps here
                //run only the smoke tags
              bat 'mvn test -Dcucumber.filter.tags=@smoke -Dbrowser=%browser%'
              

            }
        }
        stage('Archive artifact the Jar file') {
            steps {
      //Archive the JAR file as an artifact after build:
      //The artifact can be easily downloaded and shared with others like for testing or analysis.
       archiveArtifacts 'target/*.jar'
                // Trigger downstream job
build job: 'ProjectB', parameters: [string(name: 'APP_JAR', value: 'LibraryAppAllInOne-1.0-SNAPSHOT.jar')]

// Scripted call 
bat "C:/ProgramData\Jenkins/.jenkins/workspace/firstPipeLIne/target/LibraryAppAllInOne-1.0-SNAPSHOT.jar"

// Copy artifact
copyArtifacts(projectName: 'firstPipeLIne ', target: 'libs\\\\', flatten: true)
bat "java -jar libs\\my-app.jar"

// Shared library
@Library('shared-libs') _
runApp(APP_JAR)

// Artifact repository
bat "mvn deploy:deploy-file -Dfile=C:/ProgramData\Jenkins/.jenkins/workspace/firstPipeLIne/target/LibraryAppAllInOne-1.0-SNAPSHOT.jar -DrepositoryId=myRepo"
bat "java -jar my-app:1.0-SNAPSHOT"

// Shared filesystem
bat "copy my-app.jar \\server\\share" 
bat "java -jar \\server\\share\\LibraryAppAllInOne-1.0-SNAPSHOT.jar
              

            }
        }
    }

    post {
        always{
            
            
             junit 'target/**/*.xml'
             
             archiveArtifacts 'target/**/*.xml'
             
             
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

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
              bat 'mvn test -Dcucumber.filter.tags=@smoke-Dbrowser=chrome'
              

            }
        }
     stage('Copy JAR to Target Repository') {
            steps {
                // Copy the JAR file to the target repository
                bat 'cp LibraryAppAllInOne-1.0-SNAPSHOT.jar /target'
            }
        }
        stage('Run JAR') {
            steps {
                // Navigate to the target repository directory and run the JAR file
                dir('/target') {
                    bat 'java -jar LibraryAppAllInOne-1.0-SNAPSHOT.jar'
                }
            }
}

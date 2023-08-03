pipeline {
    agent any

    stages {
                // Navigate to the target repository directory and run the JAR file
                dir('/target') {
                    bat 'java -jar LibraryAppAllInOne-1.0-SNAPSHOT.jar'
                }
            }
    }


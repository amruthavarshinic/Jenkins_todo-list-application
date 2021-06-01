pipeline {

 agent {
   node {
     label 'agent'
   }
 }

    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
            }
        }
    }

 post {
   always {
     echo "Post Action"
   }
 }
 pipeline {
  agent any

  options {
    disableConcurrentBuilds()
  }

  environment {
    PROJECT_NAME = "TODOAPP"
    UBUNTU_SSH_CRED = credentials('UBUNTU-SSH')
  }
}
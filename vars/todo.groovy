def call(Map params = [:]) {
  // Start Default Arguments
  def args = [
          NEXUS_IP               : '172.31.52.12',
  ]
  
  args << params

  // End Default + Required Arguments
  
  pipeline {
    agent {
      label "${args.SLAVE_LABEL}"
    }

    environment {
      COMPONENT     = "${args.COMPONENT}"
      NEXUS_IP      = "${args.NEXUS_IP}"
      PROJECT_NAME  = "${args.PROJECT_NAME}"
      SLAVE_LABEL   = "${args.SLAVE_LABEL}"
      APP_TYPE      = "${args.APP_TYPE}"
    }

   stages {

    stage('Build Code & Install Dependencies') {
        script {
            build = new nexus()
            build.code_build ("${APP_TYPE}", "${COMPONENT}")
        }
    
    }
    
    stage('Build Code & Install Dependencies') {
      steps {
        script {
            prepare = new nexus()
            prepare.make_artifacts ("${APP_TYPE}", "${COMPONENT}")
        }
 
      }
    }    

    stage('Upload Artifact') {
      steps {
        sh '''
         curl -v -u admin:admin123 --upload-file /home/ubuntu/workspace/TODO_CI-Pipelines/login.zip http://172.31.52.12:8081/repository/login/login.zip
        '''
      }
    }
  }
 }
  
}
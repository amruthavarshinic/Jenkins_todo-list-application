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

    stage('Downloading Dependencies') {
      when {
          environment name: 'APP_TYPE', value: 'NODEJS'
      }
      steps {
        sh '''
          npm install
        '''
      }
    }
    
    stage('Prepare Artifacts') {
      when {
          environment name: 'APP_TYPE', value: 'NODEJS'      
      }
      steps {
        sh '''
          zip ../${COMPONENT}.zip *
        '''
      }
    }

    stage('Upload Artifact') {
      steps {
        script {
           nexus
        }
      }
    }
  }

 }
  
} 
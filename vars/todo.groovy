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

      stage('Prepare Artifacts') {
      stage('Prepare Artifacts - forntend') {
        when {
          environment name: 'APP_TYPE', value: 'NODEJS'
        }
        steps {
          sh '''
          cd static
          zip -r ../${COMPONENT}.zip node_modules server.js
        '''
        }
      }
      stage('Download Dependencies') {
        when {
          environment name: 'APP_TYPE', value: 'NODEJS'
        }
        steps {
          sh '''
          npm install
        '''
        }
      }

      stage('Prepare Artifacts - todo') {
        when {
          environment name: 'APP_TYPE', value: 'NODEJS'
        }
        steps {
          sh '''
          zip -r ${COMPONENT}.zip node_modules server.js
        '''
        }
      }
      stage('Upload Artifacts') {
        steps {
          sh '''
          curl -f -v -u admin:admin123 --upload-file frontend.zip http://${NEXUS_IP}:8081/repository/frontend/frontend.zip
        '''
        }
      }
    }
  }
   
}
}
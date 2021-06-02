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

    stage ('Downloade Dependecies - frontend') {
        when {
            environment name: 'APP_TYPE', value: 'NGINX'
        }

        steps {
            sh '''
              npm install
            '''
        }
    
    }

    stage('Prepare Artifacts - frontend') {
        when {
            environment name: 'APP_TYPE', value: 'NGINX'  
        }
        
        steps {
            sh '''
              zip ../${COMPONENT}.zip node_modules server.js
            '''
        }

    }

    stage('Downloade Dependecies - login') {
      when {
          environment name: 'APP_TYPE', value: 'GO'
      }
      steps {
        sh '''
          go build
        '''  
      }
    }
    
    stage('Prepare Artifacts - login') {
      when {
          environment name: 'APP_TYPE', value: 'GO'          
      }
      steps {
        sh '''
          zip ../${COMPONENT} *
        '''
      }
    }

    stage ('Downloade Dependecies - todo') {
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
              zip ../${COMPONENT}.zip node_modules server.js
            '''
        }

    }

    stage('Compile Code') {
      when {
          environment name: 'APP_TYPE', value: 'JAVA'
      }      
      steps {
        sh '''
          mvn compile
        '''
      }
    }

    stage('Make Package') {
      when {
          environment name: 'APP_TYPE', value: 'JAVA'
      }        
      steps {
        sh '''
          mvn package
        '''
      }
    }

    stage('Prepare Artifacts') {
      when {
          environment name: 'APP_TYPE', value: 'JAVA'
      }
      steps {
        sh '''
          cp target/*.jar users.jar 
          zip ../users.zip users.jar
        '''
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
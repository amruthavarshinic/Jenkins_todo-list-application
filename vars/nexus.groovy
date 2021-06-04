def nexus(COMPONENT) {
  get_branch = "env | grep GIT_BRANCH | awk -F / '{print \$NF}' | xargs echo -n"
  def get_branch_exec=sh(returnStdout: true, script: get_branch)
  def FILENAME=COMPONENT+'-'+get_branch_exec+'.zip'

  command = "curl -f -v -u admin:admin123 --upload-file /home/ubuntu/workspace/TODO_CI-Pipelines/${COMPONENT}-ci/${FILENAME} http://172.31.52.12:8081/repository/${COMPONENT}/${FILENAME}"
  def execute_state=sh(returnStdout: true, script: command)
}

def make_artifacts(APP_TYPE, COMPONENT) {
  get_branch = "env | grep GIT_BRANCH | awk -F / '{print \$NF}' | xargs echo -n"
  def get_branch_exec=sh(returnStdout: true, script: get_branch)
  def FILENAME=COMPONENT+'-'+get_branch_exec+'.zip'
  if(COMPONENT == "frontend") {
    command = "zip -r ${FILENAME} *"
    def execute_com=sh(returnStdout: true, script: command)
    print execute_com
  } else if(COMPONENT == "todo") {
    command = "zip -r ${FILENAME} *"
    def execute_com=sh(returnStdout: true, script: command)
    print execute_com
  } else if(COMPONENT == "users") {
    command = "cp target/*.jar ${COMPONENT}.jar && zip -r ${FILENAME} ${COMPONENT}.jar"
    def execute_com=sh(returnStdout: true, script: command)
    print execute_com
  } else if(COMPONENT == "login") {
    command = "zip -r ${FILENAME} *"
    def execute_com=sh(returnStdout: true, script: command)
    print execute_com
  }    
}

def code_build(APP_TYPE, COMPONENT) {
  if(COMPONENT == "frontend") {
    command = "npm install && npm run build"
    def execute_com=sh(returnStdout: true, script: command)
    print execute_com
  } else if(COMPONENT == "users") {
    command = "mvn clean package"
    def execute_com=sh(returnStdout: true, script: command)
    print execute_com
  } else if(COMPONENT == "todo") {
    command = "npm install"
    def execute_com=sh(returnStdout: true, script: command)
    print execute_com
  }else if(COMPONENT == "login") {
    command = "go build"
    def execute_com=sh(returnStdout: true, script: command)
    print execute_com
  }

}
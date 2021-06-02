def nexus() {
  command = "curl -f -v -u admin:admin123 --upload-file /home/ubuntu/workspace/TODO_CI-Pipelines/todo.zip http://172.31.52.12:8081/repository/todo/todo1.zip"
  def execute_state=sh(returnStdout: true, script: command)
}
//pipelineJob : Component
// url : github url
// brach name : */main 
// scriptPath : Jenkinsfile

pipelineJob('frontend-ci') {
  folder('TODO_CI-Pipelines') {
    displayName('TODO_CI-Pipelines')
    description('TODO_CI-Pipelines')
  }


  pipelineJob('TODO_CI-Pipelines/frontend-ci') {
    configure { flowdefinition ->
      flowdefinition << delegate.'definition'(class: 'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition', plugin: 'workflow-cps') {
        'scm'(class: 'hudson.plugins.git.GitSCM', plugin: 'git') {
          'userRemoteConfigs' {
            'hudson.plugins.git.UserRemoteConfig' {
              'url'('https://github.com/zsdevops01/frontend.git')
            }
          }
          'branches' {
            'hudson.plugins.git.BranchSpec' {
              'name'('*/main')
            }
          }
        }
        'scriptPath'('Jenkinsfile')
        'lightweight'(true)
      }
    }
  }
}
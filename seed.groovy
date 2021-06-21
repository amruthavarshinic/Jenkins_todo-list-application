// This script for components jobs

folder('TODO_CI-Pipelines') {
  displayName('TODO_CI-Pipeliness')
  description('TODO_CI-Pipelines')
}

def component = ["todo", "login","users","frontend"];

def count=(component.size()-1)
for (i in 0..count) {
  def j=component[i]
  pipelineJob("TODO_CI-Pipelines/${j}-ci") {
    configure { flowdefinition ->
      flowdefinition / 'properties' << 'org.jenkinsci.plugins.workflow.job.properties.PipelineTriggersJobProperty' {
        'triggers' {
          'hudson.triggers.SCMTrigger' {
            'spec'('*/2 * * * 1-5')
            'ignorePostCommitHooks'(false)
          }
        }
      }
      flowdefinition << delegate.'definition'(class:'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition',plugin:'workflow-cps') {
        'scm'(class:'hudson.plugins.git.GitSCM',plugin:'git') {
          'userRemoteConfigs' {
            'hudson.plugins.git.UserRemoteConfig' {
              'url'('https://github.com/zs-amrutha/'+j+'.git')
              'refspec'('\'+refs/tags/*\':\'refs/remotes/main/tags/*\'')
            }
          }
          'branches' {
            'hudson.plugins.git.BranchSpec' {
              'name'('*/tags/*')
            }
          }
        }
        'scriptPath'('Jenkinsfile')
        'lightweight'(true)
      }
    }
  }
}

// This script for Deployment job 

pipelineJob("TODO_Deployment-Pipeline") {
  configure { flowdefinition ->

    flowdefinition << delegate.'definition'(class:'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition',plugin:'workflow-cps') {
      'scm'(class:'hudson.plugins.git.GitSCM',plugin:'git') {
        'userRemoteConfigs' {
          'hudson.plugins.git.UserRemoteConfig' {
            'url'('https://github.com/zs-amrutha/Jenkins.git')
          }
        }
        'branches' {
          'hudson.plugins.git.BranchSpec' {
            'name'('main')
          }
        }
      }
      'scriptPath'('Jenkinsfile')
      'lightweight'(true)
    }
  }
}
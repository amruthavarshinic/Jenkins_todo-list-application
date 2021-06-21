pipeline {
    agent {
        label "ANSIBLE"
    }

    environment {
        UBUNTU_SSH_PASSWORD = credentials('UBUNTU_SSH_PASSWORD')
    }

    parameters {
        choice(name: 'ENV', choices: ['dev', 'prod' ], description: 'Select Environment')
        string(name: 'COMPONENT', defaultValue: '', description: 'Which Component to deploy')
        string(name: 'VERSION', defaultValue: '', description: 'Which Version of Component to deploy')
    }

    stages {

        stage('Find the Server') {
            steps {
                sh 'aws ec2 describe-instances --filters "Name=tag:Name,Values=frontend-dev" --region us-east-1' 

            }
        }

        stage('Deploy to DEV Env') {
            when {
                environment name: 'ENV', value: 'dev'
            }
            steps {
                git branch: 'main', credentialsId: 'git-cred', url: 'https://github.com/zs-amrutha/Ansible.git'
                sh '''
                    ansible-playbook -i 172.31.4.228, todo.yml -t ${COMPONENT} -e COMPONENT=${COMPONENT} -e ENV=${ENV} -e APP_VERSION=${VERSION} -e ansible_password=${UBUNTU_SSH_PASSWORD}
                '''
            }
        }

        stage('Deploy to PROD Env') {
            when {
                environment name: 'ENV', value: 'prod'
            }
            steps {
                sh 'echo ansible-playbook .....'
            }
        }

    }
}
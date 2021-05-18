pipeline {
    agent any


    parameters { 
        string(defaultValue: "https://github.com", description: 'Whats the github URL?', name: 'URL')
        
    }

stages{  
    stage('checkout scm') {
            steps {
                script{
                    git credentialsId: 'devops-github-pecos-jenkins-rw-token', url: "${params.URL}"
                    sh 'git branch -r | awk \'{print $1}\' ORS=\'\\n\' >>branch.txt'
                }

            }
        }
        stage('get build Params User Input') {
            steps{
                script{

                    liste = readFile 'branch.txt'
                    echo "please click on the link here to chose the branch to build"
                    env.BRANCH_SCOPE = input message: 'Please choose the branch to build ', ok: 'Validate!',
                            parameters: [choice(name: 'BRANCH_NAME', choices: "${liste}", description: 'Branch to build?')]


                }
            }
        } 
            stage("checkout the branch"){
                steps{
                    echo "${env.BRANCH_SCOPE}"
                    git  credentialsId: 'devops-github-pecos-jenkins-rw-token', url: "${params.URL}"
                    #sh 'git remote update'
                    #sh 'git fetch'
                    #sh "git switch -b ${env.BRANCH_NAME}"
                    echo "${env.BRANCH_NAME}"
                }
           }
    }    
}
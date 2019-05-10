pipeline {
    environment {
        registryName = "sandjaie/jenkins-ci"
    }
    agent any

    stages {
        stage('Prepration') {
            steps {
                script {
                    checkout scm
                    sh "git rev-parse --short HEAD > .git/commit-id"
                    commit_id = readFile('.git/commit-id').trim()
                }
            }
        }
        stage('Building Image') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub') {
                        docker.build("${registryName}:${commit_id}", '.').push()
                    }
                }
            }
        }
    }
}
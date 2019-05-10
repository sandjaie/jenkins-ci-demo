pipeline {
    environment {
        registryName = "sandjaie/jenkins-ci"
        registryCredential = 'dockerhub'
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
                    dockerImage = docker.build("${registryName}:${commit_id}")
                }
            }
        }
        stage('Push Image') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', registryCredential) {
                        dockerImage.push()
                        dockerImage.push("latest")
                    }
                }
            }
        }
        stage('Docker Purge') {
            steps {
                script {
                    sh 'docker image prune -f'
                }
            }
        }
    }
    post {
        always {
            echo 'Cleaning up workspace'
            deleteDir()
        }
    }
}
pipeline{
    agent any

    environment{
        registry = "enzotrevisan123/covid-data"
        registryCredential = "dockerhub_id"
        dockerImage = ""
    }

    stages {
        stage('Clone Repository'){
            steps {
                git branch: "main", url: 'https://gitlab.com/enzox123/app-dcw5.git'
            }
        }
        stage('Build Docker Image'){
            steps{
                script{
                    dockerImage = docker.build registry + ":develop"
                }
            }
        }
        stage('Send Image to Docker Hub'){
            steps{
                script{
                    docker.withRegistry('', registryCredential){
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Cleaning up'){
            steps{
                sh "docker rmi $registry:develop"
            }
        }
    }
}
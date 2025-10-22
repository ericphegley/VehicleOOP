pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "vehicle-oop:latest"
    }
    stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'github-creds', url: 'https://github.com/ericphegley/VehicleOOP.git'
            }
        }
        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Docker Build') {
            steps {
                sh "docker build -t $DOCKER_IMAGE ."
            }
        }
        stage('Run Container') {
            steps {
                sh "docker stop vehicle-oop || true"
                sh "docker rm vehicle-oop || true"
                sh "docker run -d -p 8080:8080 --name vehicle-oop $DOCKER_IMAGE"
            }
        }
    }
}

pipeline {
    agent any
    environment {
        AWS_REGION = "us-east-1"
        ACCOUNT_ID = "203918866266"
        REPO_NAME = "vehicle-oop"
        ECR_REPO = "${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${REPO_NAME}"
        DOCKER_IMAGE = "${ECR_REPO}:latest"
        K8S_NAMESPACE = "vehicle-app"  
    }
    stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'github-creds', url: 'https://github.com/ericphegley/VehicleOOP.git'
            }
        }
        stage('Build & Test') {
            steps {
                sh 'mvn clean test package -DskipTests=false'
            }
        }
        stage('Docker Build') {
            steps {
                sh "docker build -t ${REPO_NAME}:latest ."
            }
        }
        stage('Push to ECR') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'aws-creds', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY')]) {
                    sh '''
                        aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
                        docker tag ${REPO_NAME}:latest ${DOCKER_IMAGE}
                        docker push ${DOCKER_IMAGE}
                    '''
                }

            }
        }
        stage('Configure kubectl') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'aws-creds', usernameVariable: 'AWS_ACCESS_KEY_ID', passwordVariable: 'AWS_SECRET_ACCESS_KEY')]) {
                    sh '''
                        aws eks update-kubeconfig --region ${AWS_REGION} --name vehicle-cluster
                    '''
                }
            }
        }
        stage('Deploy to EKS') {
            steps {
                sh '''
                    kubectl apply -f k8s/vehicle-deployment.yaml -n ${K8S_NAMESPACE}
                    kubectl apply -f k8s/vehicle-service.yaml -n ${K8S_NAMESPACE}
                    kubectl rollout status deployment/vehicle-oop-deployment -n ${K8S_NAMESPACE}
                '''
            }
        }
    }
}

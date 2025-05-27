pipeline {
    agent any
    environment {
        FRONTEND_IMAGE = "itsm-frontend:latest"
        BACKEND_IMAGE = "itsm-backend:latest"
        COMPOSE_FILE = 'docker-compose.yml'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Check Docker Images') {
            steps {
                script {
                    sh '''
                    IMAGE_LIST=$(docker images --format "{{.Repository}}:{{.Tag}}" | grep ":latest")

                    if [ -z "$IMAGE_LIST" ]; then
                        echo "No latest images found."
                    else
                        echo "Removing old images:"
                        echo "$IMAGE_LIST"
                        echo "$IMAGE_LIST" | while IFS= read -r IMG; do
                            echo "Removing: $IMG"
                            docker rmi -f "$IMG" || true
                        done
                    fi
                    '''
                }
            }
        }
        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    sh 'docker build -t $FRONTEND_IMAGE -f dockerfile .'
                }
            }
        }
        stage('Build Backend') {
            steps {
                dir('backend') {
                    sh 'docker build -t $BACKEND_IMAGE -f dockerfile .'
                }
            }
        }
        stage('Run Tests') {
            steps {
                dir('backend') {
                    sh './gradlew test --no-daemon'
                }
            }
        }
        stage('Deploy Frontend & Backend') {
            steps {
                // Stop only frontend and backend services
                sh "docker-compose -f $COMPOSE_FILE stop itsm-frontend itsm-backend"
                // Remove stopped frontend and backend containers
                sh "docker-compose -f $COMPOSE_FILE rm -f itsm-frontend itsm-backend"
                // Start only frontend and backend with build
                sh "docker-compose -f $COMPOSE_FILE up -d --build itsm-backend itsm-frontend"
            }
        }
    }
    post {
        always {
            echo 'Cleaning up workspace and unused Docker resources'
        }
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed. Please check the logs.'
        }
    }
}

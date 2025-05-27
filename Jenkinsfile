pipeline {
  agent any

  tools {
    jdk "jdk21"
  }

  environment {
    FRONTEND_IMAGE = "itsm-frontend:latest"
    BACKEND_IMAGE  = "itsm-backend:latest"
    COMPOSE_FILE   = 'docker-compose.yml'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Check Docker Images') {
      steps {
        sh '''
        echo "[INFO] Checking for :latest tagged Docker images..."
        IMAGE_LIST=$(docker images --format "{{.Repository}}:{{.Tag}}" | grep ":latest" || true)
        if [ -z "$IMAGE_LIST" ]; then
            echo "[INFO] No latest images found."
        else
            echo "$IMAGE_LIST" | while IFS= read -r IMG; do
                echo "[INFO] Removing: $IMG"
                docker rmi -f "$IMG" || true
            done
        fi
        '''
      }
    }

    stage('Deploy Frontend & Backend') {
      steps {
        sh '''
          echo "[INFO] Stopping existing containers..."
          docker-compose -f $COMPOSE_FILE down

          echo "[INFO] Starting up new containers..."
          docker-compose -f $COMPOSE_FILE up -d --build
        '''
      }
    }
  }

  post {
    always {
      cleanWs()
      sh '''
        echo "[INFO] Cleaning up unused Docker resources..."
        docker system prune -f
      '''
    }
    success {
      echo '[SUCCESS] Pipeline completed successfully.'
    }
    failure {
      echo '[FAILURE] Pipeline failed. Please check the logs.'
    }
  }
}

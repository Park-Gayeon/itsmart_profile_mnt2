pipeline {
  agent any

  tools {
    jdk "jdk21"
  }

  // 변경사항이 있는 경우 실행 2PM
  triggers {
      pollSCM('0 14 * * *')
  }

  environment {
    COMPOSE_FILE   = 'docker-compose.yml'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Stop and Remove containers') {
      steps {
        sh '''
          echo "[INFO] Stopping existing containers..."
          docker-compose -f $COMPOSE_FILE down
        '''
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

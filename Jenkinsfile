pipeline {
  agent any

  environment {
    FRONTEND_IMAGE = "itsm-frontend:latest"
    BACKEND_IMAGE  = "itsm-backend:latest"
    COMPOSE_FILE   = 'docker-compose.yml'
  }

  stages {
    stage('Verify Environment') {
      steps {
        script {
          docker.image('myorg/gradle-docker:jdk21').inside('-v /var/run/docker.sock:/var/run/docker.sock') {
            sh '''
              echo "=== Environment Check ==="
              java -version
              gradle -version
              docker --version
              echo "Current user: $(whoami)"
              echo "Groups: $(groups)"
            '''
          }
        }
      }
    }

    stage('Checkout') {
      steps {
        checkout scm
        sh 'ls -al && git branch'
        sh 'docker ps'
      }
    }

    stage('Check Docker Images') {
      steps {
        sh '''
        IMAGE_LIST=$(docker images --format "{{.Repository}}:{{.Tag}}" | grep ":latest")
        if [ -z "$IMAGE_LIST" ]; then
            echo "No latest images found."
        else
            echo "$IMAGE_LIST" | while IFS= read -r IMG; do
                echo "Removing: $IMG"
                docker rmi -f "$IMG" || true
            done
        fi
        '''
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

    stage('Deploy Frontend & Backend') {
      steps {
        sh '''
          docker-compose -f $COMPOSE_FILE stop itsm-frontend itsm-backend
          docker-compose -f $COMPOSE_FILE rm -f itsm-frontend itsm-backend
          docker-compose -f $COMPOSE_FILE up -d --build itsm-backend itsm-frontend
        '''
      }
    }
  }

  post {
    always {
      cleanWs()
      sh 'docker system prune -f'
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

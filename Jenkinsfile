pipeline {
  agent {
    docker {
      // Docker 데몬 소켓 + 커맨드 바이너리 마운트 대신
      // 미리 docker-cli 포함된 커스텀 에이전트 이미지 사용을 권장
      image 'myorg/gradle-docker:jdk21'
      reuseNode true
      args '-v /var/run/docker.sock:/var/run/docker.sock'
    }
  }

  environment {
    FRONTEND_IMAGE = "itsm-frontend:latest"
    BACKEND_IMAGE  = "itsm-backend:latest"
    COMPOSE_FILE   = 'docker-compose.yml'
  }

  stages {
    stage('Verify Environment') {
      steps {
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
    stage('Checkout') {
        steps {
            echo "steps 진입"
            checkout scm
            sh 'ls -al && git branch'
            echo "git branch"
            sh 'docker ps'
            echo "docker ps"
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
            sh "docker-compose -f $COMPOSE_FILE rm -f itsm-frontend itsm-frontend"
            // Start only frontend and backend with build
            sh "docker-compose -f $COMPOSE_FILE up -d --build itsm-backend itsm-frontend"
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

# Jenkins with Docker and JDK21 support
FROM jenkins/jenkins:lts

# root 권한으로 전환
USER root

# 기본 패키지 업데이트 및 필수 도구 설치
RUN apt-get update && apt-get install -y \
    ca-certificates \
    curl \
    gnupg \
    lsb-release \
    wget

# JDK 21 설치
RUN wget -O - https://apt.corretto.aws/corretto.key | apt-key add - && \
    echo "deb https://apt.corretto.aws stable main" | tee /etc/apt/sources.list.d/corretto.list && \
    apt-get update && \
    apt-get install -y java-21-amazon-corretto-jdk

# JAVA_HOME 환경변수 설정
ENV JAVA_HOME=/usr/lib/jvm/java-21-amazon-corretto
ENV PATH=$JAVA_HOME/bin:$PATH

# Docker 공식 저장소 추가
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
RUN echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null

# Docker CLI 설치
RUN apt-get update && apt-get install -y docker.io -y

# Jenkins 사용자를 docker 그룹에 추가
RUN usermod -aG docker jenkins

# 권한 정리
RUN apt-get clean && rm -rf /var/lib/apt/lists/*

# Jenkins 사용자로 전환
USER jenkins

# Jenkins에서 JDK 21을 기본으로 사용하도록 설정
ENV JENKINS_JAVA_CMD=$JAVA_HOME/bin/java
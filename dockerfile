# Jenkins with Docker and JDK17 support
FROM jenkins/jenkins:lts-jdk17

# Switch to root to install packages
USER root

# Update base packages and install dependencies
RUN apt-get update && apt-get install -y \
    ca-certificates \
    curl \
    gnupg \
    lsb-release \
    wget && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Docker official repository setup
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg && \
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian $(lsb_release -cs) stable" \
      | tee /etc/apt/sources.list.d/docker.list > /dev/null

# Install Docker CLI
RUN apt-get update && apt-get install -y --no-install-recommends docker.io && rm -rf /var/lib/apt/lists/*

# Add Jenkins user to docker group for socket access
RUN usermod -aG docker jenkins

# Revert to Jenkins user
USER jenkins

# Ensure Jenkins uses the container's Java (JDK17)
ENV JENKINS_JAVA_CMD=/usr/local/openjdk-17/bin/java
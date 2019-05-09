# CICD with jenkins

Install jenkins in a container with 

# Installing Docker on a ubuntu machine
$ sudo apt-get update

$ sudo apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D

$ sudo apt-add-repository 'deb https://apt.dockerproject.org/repo ubuntu-xenial main'

$ sudo apt-get update

$ sudo apt-get install -y docker-engine

$ sudo systemctl enable docker

$ sudo usermod -a -G docker ubuntu

Check here for Ubuntu 18.04: https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-18-04

# Installing Docker on Amazon Linux
$ sudo yum update -y

$ sudo amazon-linux-extras install docker

$ sudo usermod -a -G docker ec2-user


# Install Jenkins
$ sudo mkdir -p /var/jenkins_home

$ sudo chown -R 1000:1000 /var/jenkins_home/

$ docker run -p 8080:8080 -p 50000:50000 -v /var/jenkins_home:/var/jenkins_home --name jenkins -d jenkins/jenkins:lts


# To access docker.sock on the host machine
Build the jenkins docker image using the Dockerfile

Always check 'ls -lrt /var/run/docker.sock' user and group. Change the group ID of docker accordingly in the dockerfile before building.

$ docker run -p 8080:8080 -p 50000:50000 -v /var/jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock --name jenkins -d jenkins-docker:latest


# To start the jenkins container when docker service is restarted or started

$ docker run -p 8080:8080 -p 50000:50000 -v /var/jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock --name jenkins  --restart unless-stopped -d jenkins-docker:latest

# CI with jenkins

Build a jenkins container and run jobs.

### Installing Docker on a ubuntu machine
```
$ sudo apt-get update
$ sudo apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D
$ sudo apt-add-repository 'deb https://apt.dockerproject.org/repo ubuntu-xenial main'
$ sudo apt-get update
$ sudo apt-get install -y docker-engine
$ sudo systemctl enable docker
$ sudo usermod -a -G docker ubuntu
```

Check here for Ubuntu 18.04: https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-18-04

### Installing Docker on Amazon Linux
`$ sudo yum update -y`<br>
`$ sudo amazon-linux-extras install docker`<br>
`$ sudo usermod -a -G docker ec2-user`<br>


### Installing Jenkins
```$ sudo mkdir -p /var/jenkins_home```<br>
```$ sudo chown -R 1000:1000 /var/jenkins_home/```<br>
```$ docker pull jenkins/jenkins:lts```<br>
```$ docker run -p 8080:8080 -p 50000:50000 -v /var/jenkins_home:/var/jenkins_home --name jenkins -d jenkins/jenkins:lts```<br>


### To access docker.sock on the host machine
Build the jenkins docker image using the Dockerfile

Always check `'ls -lrt /var/run/docker.sock'` user and group. Change the group ID of docker accordingly in the dockerfile before building.

Build jenkins from the Dockerfile and run with shared volume so docker.sock is accessible to jenkins container.

```$ docker build -t jenkins-docker .```

```$ docker run -p 8080:8080 -p 50000:50000 -v /var/jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock --name jenkins -d jenkins-docker:latest```


### To start the jenkins container when docker service is restarted or started

```$ docker run -p 8080:8080 -p 50000:50000 -v /var/jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock --name jenkins  --restart unless-stopped -d jenkins-docker:latest```


### To extract the list of installed plugins
source https://github.com/jenkinsci/docker

```
JENKINS_HOST=username:password@myhost.com:port

curl -sSL "http://$JENKINS_HOST/pluginManager/api/xml?depth=1&xpath=/*/*/shortName|/*/*/version&wrapper=plugins" | perl -pe 's/.*?<shortName>([\w-]+).*?<version>([^<]+)()(<\/\w+>)+/\1 \2\n/g'|sed 's/ /:/'
```

Or run the following script in your jenkins<br>
http://localhost:8080/script

```
Jenkins.instance.pluginManager.plugins.each{
  plugin -> 
    println ("${plugin.getShortName()}: ${plugin.getVersion()}")
}
```
Add the required job-dsl groovy scripts from the job-dsl-samples directory to your repo and create a seed job in your jenkins.

Seed job specifications: <br>
Set the following:
* Source Code Management: 
    * Git
        * Repository URL : Add the repo here
* Build
    * Process Job DSLs
        *  Look on filesystem : Add the scripts path here


My docker hub repository 
where the container gets pushed<br>
https://hub.docker.com/r/sandjaie/nodejs-docker-example/tags

My sample forked nodejs project:<br>
https://github.com/sandjaie/nodejs-docker-demo<br>
I use the above repo to test the automation, under 'jobs-dsl' and 'jenkins-pipeline' the scripts are placed.

#### To run the groovy scripts as admin, to avoid the manual approval every time there is change in the groovy script, configure 'authorize-project' plugin and run the script as admin

### Slack Intergration
plugin: Slack Notification Plugin

In you slack workspace add the app 'incoming webhooks' to the 'alerts' channel.<br>
Go to Manage Jenkins -> Configue System ->'Global Slack Notifier Settings' <br>
* Slack compatible app URL: https://hooks.slack.com/services/
* Team Subdomain: 'your slack worspace domain'
* Intergration Token Credentials ID: 'webhook token' add it as a secret (find it in the webhook url in setup instructions section in slack)
* Channel or Slack ID : 'your channel'<br>
Use Jenkinsfile.v3 sample file

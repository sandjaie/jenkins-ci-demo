def project = 'nodejs-docker-demo'
def jobName = "${project}"
def registryName = 'sandjaie/nodejs-docker-example'

job(jobName) {
    scm {
        git("git://github.com/sandjaie/${project}.git") {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('sandjaie')
            node / gitConfigEmail('sandjaie@gmail.com')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs') // this is the name of the NodeJS installation in
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        dockerBuildAndPublish {
            repositoryName("${registryName}")
            tag('${GIT_REVISION,length=8}')
            registryCredentials('dockerhub')
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}

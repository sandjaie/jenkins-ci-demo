def project = 'nodejs-docker-demo'
def jobName = "${project}"

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
        shell("npm install")
    }
}

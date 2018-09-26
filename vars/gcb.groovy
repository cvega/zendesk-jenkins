def call(Map config = [:]) {
    echo "Hello Library"
    googleCloudBuild credentialsId: "${config.credential}", source: local("${config.source}"), request: file('cloudbuild.yaml')
}

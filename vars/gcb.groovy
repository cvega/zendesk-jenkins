def call(Map config = [:]) {
    googleCloudBuild \
      credentialsId: ${config.credential},
      source: local(${config.source}),
      request: file('cloudbuild.yaml')
}

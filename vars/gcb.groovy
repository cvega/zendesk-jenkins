def call(Map config = [:]) {
  try {
    googleCloudBuild \
      credentialsId: ${config.credential},
      source: local(${config.source}),
      request: file('cloudbuild.yaml')
  }
  catch(err) {
    echo "gcb failed: ${err}"
  }
}

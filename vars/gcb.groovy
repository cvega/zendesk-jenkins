def call(Map config = [:]) {
  echo "Hello GCB Library"
  try {
    googleCloudBuild \
      credentialsId: "${config.credentialId}",
      source: repo(projectId: "${config.credentialId}",
                    repoName: "hello-cje",
                         tag: "${config.tag}", 
                   commit: "${env.GIT_COMMIT}"),
      request: file("${config.cloudBuildFile}")
  }
  catch (err) {
    echo "GoogleCloudBuilder failed: ${err}"
  }
}

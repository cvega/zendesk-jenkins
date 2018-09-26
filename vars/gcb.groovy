def call(Map config = [:]) {
  echo "Hello GCB Library"
  try {
    googleCloudBuild \
      credentialsId: "${config.credentialsId}",
      source: repo(projectId: "${config.credentialsId}",
                    repoName: "hello-cje",
                         tag: "${config.tag}", 
                   commit: "${env.GIT_COMMIT}"),
      request: file("${config.cloudBuildFile}")
  }
  catch (err) {
    echo "GoogleCloudBuilder failed: ${err}"
  }
}

def call(Map config = [:]) {
  try {
    googleCloudBuild \
      credentialsId: "${config.credentialsId}",
      source: repo(projectId: "${config.credentialsId}",
                    repoName: "hello-cje",
                      commit: "${env.GIT_COMMIT}"),
      request: file("${config.cloudBuildFile}")
  }
  catch (err) {
    echo "GoogleCloudBuilder failed: ${err}"
    throw
  }
}

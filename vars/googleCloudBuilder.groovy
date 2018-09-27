def call(Map config = [:]) {
    try {
        googleCloudBuilder(
                credentialsId: ${config.credentialsId},
                source: repo(projectId: ${config.projectId}, repoName: ${config.repoName},
                        tag: ${config.tag}, commit: ${config.commitRef}),
                request: file(${config.cloudBuildFile})
        )
    }
    catch (err) {
        echo "GoogleCloudBuilder failed: ${err}"
    }
}

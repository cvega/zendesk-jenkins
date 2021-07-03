def call(Map pipelineParams) {

    node {
        //This could be run in parallel but for now as seqence
        stage ("build-docker-image") {
            echo "Call GoogleCloudBuilder libarary function for ${pipelineParams.repoName}"
        }
        stage ("Travis test") {
            echo "Call Travis API to unit test the build ${pipelineParams.repoName}"
        }
        stage ("Deploy to staging") {
            echo "Call Samson using the generic CI webhook https://${pipelineParams.samsonServer}/integrations/generic/${pipelineParams.webhookId}"
        }
        stage ("Run testing") {
            echo "Call jenkins.zd-mini.com (${pipelineParams.testServer}) webhook to run a test ${pipelineParams.stageTest}"
        }
        stage ("Deploy to production") {
            echo "A manual step to navigate to Samson (${pipelineParams.samsonServer}) to deploy to production"
        }
        stage("Verify production deployment") {
            echo "A manual step to verify that Samson completed the deployment successfully."
            echo "This should be wrapped into the deploy to production step"
        }
        stage("Run production tests"){
            echo "Call jenkins.zd-mini.com (${pipelineParams.testServer}) webhook to run a production test ${pipelineParams.prodTest}"
        }

    }
}

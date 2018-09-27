import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification

public class GoogleCloudBuilderSpec extends JenkinsPipelineSpecification {

    def GoogleCloudBuilder = null

    def setup() {
        GoogleCloudBuilder = loadPipelineScriptForTest("vars/GoogleCloudBuilder.groovy")
    }

    def "Trigger the GCB build"() {
        when:
                GoogleCloudBuilder(credentialsId: "jenkins-poc-1234", projectId: "jenkins-poc-1234", repoName: "hello-cje", tag: null, commit: "master", cloudBuildFile: "clouldbuild.yaml")
        then:
                1 * getPipelineMock("GoogleCloudBuilder")({it =~/.* SUCCESS .*/})
    }
}

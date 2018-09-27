import com.homeaway.devtools.jenkins.testing.JenkinsPipelineSpecification

public class DefaultPipelineSpec extends JenkinsPipelineSpecification {

    def DefaultPipeline = null

    public static class DummyException extends RuntimeException {
        public DummyException(String _message) { super(_message); }
    }

    def setup() {
        DefaultPipeline = loadPipelineScriptForTest("vars/DefaultPipeline.groovy")
        DefaultPipeline.getBinding().setVariable("scm",null)
        getPipelineMock("libraryResource")(_) >> {
            return "Dummy Message"
        }
    }

    def "GCB build is triggered" () {
        when:
                getPipelineMock()

        then:
              1 * getPipelineMock("docker-image-build")
    }
}


import groovy.json.JsonSlurperClassic


def call(Map conf = [:]) {
    
  if (!conf.message) {
      conf.message = "${env.BUILD_URL}"
  }
  
  def msg = """
    {
        "deploy": {
            "branch": ${env.BRANCH_NAME}
            "commit": {
                "sha": ${env.GIT_COMMIT},
                "message": ${conf.message}
            }
        }
    }
  """
    
  def req = new URL("http://samson.zd-mini.com/integrations/generic/${conf.ci_webhook}").openConnection();
  req.setRequestMethod("POST")
  req.setDoOutput(true)
  req.setRequestProperty("Content-Type", "application/json")
  req.setRequestProperty("Authorization", "Basic ${conf.token}")
  req.getOutputStream().write(msg.getBytes("UTF-8"));
  
  def resp = req.getResponseCode();
  if(resp.equals(200)) {
    def data = new JsonSlurperClassic().parseText(req.getInputStream().getText())
  }  
}


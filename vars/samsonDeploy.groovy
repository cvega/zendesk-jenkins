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
    
  def reqDeploy = new URL("http://samson.zd-mini.com/integrations/generic/${conf.ci_webhook}").openConnection();
  reqDeploy.setRequestMethod("POST")
  reqDeploy.setDoOutput(true)
  reqDeploy.setRequestProperty("Content-Type", "application/json")
  reqDeploy.setRequestProperty("Authorization", "Basic ${conf.token}")
  reqDeploy.getOutputStream().write(msg.getBytes("UTF-8"));
  
  def respDeploy = reqDeploy.getResponseCode();
  if(respDeploy.equals(200)) {
    def dataDeploy = new JsonSlurperClassic().parseText(reqDeploy.getInputStream().getText())
  }
    
  def repoName = build.environment.get("GIT_URL").replaceAll('https://github.com/zendesk/', '').replaceAll('.git', '')"
  retry(3) {
    def reqVerify = new URL("http://samson.zd-mini.com/projects/${repoName}/deploys/180.json").openConnection();
    reqVerify.setRequestMethod("GET")
    reqVerify.setRequestProperty("Content-Type", "application/json")
    reqVerify.setRequestProperty("Authorization", "Bearer ${conf.token}")
    reqVerify.getOutputStream()
    
    def respVerify = reqVerify.getResponseCode();
    if(respVerify.equals(200)) {
    def dataVerify = new JsonSlurperClassic().parseText(reqVerify.getInputStream().getText())
        println "${dataVerify}"
    }
}


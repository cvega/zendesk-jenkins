import groovy.json.JsonSlurperClassic
import groovy.json.JsonBuilder


def call(Map conf = [:]) {
  def builder = new groovy.json.JsonBuilder()
  def json = builder.deploy {
    branch "${env.BRANCH_NAME}"
    commit (
      sha: "${env.GIT_COMMIT}",
      message: "${conf.message}"
    )
  }
  
  println json.toString()
  
  def msg = "{\"deploy\":{\"branch\": \"${env.BRANCH_NAME}\",\"commit\": {\"sha\":\"${env.GIT_COMMIT}\",\"message\":\"hello!\"}}}"  
  
  def req = new URL("http://samson.zd-mini.com/integrations/generic/${config.ci_webhook}").openConnection();
  req.setRequestMethod("POST")
  req.setDoOutput(true)
  req.setRequestProperty("Content-Type", "application/json")
  req.setRequestProperty("Authorization", "Basic ${config.token}")
  req.getOutputStream().write(msg.getBytes("UTF-8"));
  
  def resp = req.getResponseCode();
  if(resp.equals(200)) {
    def data = new JsonSlurperClassic().parseText(req.getInputStream().getText())
  }
  
  
}

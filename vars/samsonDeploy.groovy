import groovy.json.JsonSlurperClassic


def call(Map config = [:]) {
  def msg = "{\"deploy\":{\"branch\": \"${env.BRANCH_NAME}\",\"commit\": {\"sha\":\"${env.GIT_COMMIT}\",\"message\":\"hello!\"}}}"
  
  println "${msg}"
  def req = new URL("http://samson.zd-mini.com/integrations/generic/${config.ci_webhook}").openConnection();
    req.setRequestMethod("POST")
    req.setDoOutput(true)
    req.setRequestProperty("Content-Type", "application/json")
    req.setRequestProperty("Authorization", "Basic ${config.token}")
    req.getOutputStream().write(msg.getBytes("UTF-8"));
    def postRC = req.getResponseCode();
    if(postRC.equals(200)) {
      def data = new JsonSlurperClassic().parseText(req.getInputStream().getText())
      println "${data.deploy_ids}"
    }
}

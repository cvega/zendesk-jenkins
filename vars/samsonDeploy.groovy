import zendesk.samson.Deploy


def call(Map conf = [:]) {
    def samson = new Deploy(this, conf.host, conf.token, conf.webhook, conf.msg);
    def deploy = samson.createDeploy();
    def status = samson.getDeploy("${deploy.deploy_ids[0]}");
    println "${status}"
}

import zendesk.samson.Deploy


def call(Map conf = [:]) {
    if (!conf.retries) { conf.retries = 5 }
    if (!conf.seconds) { conf.seconds = 30 }
    
    // init samson and create deploy
    def samson = new Deploy(this, conf.host, conf.token, conf.webhook, conf.msg, conf.repo);
    def deploy = samson.createDeploy();

    // determine samson deploy status, with retries and sleep timer
    def state, status
    for (int i = 0; i <conf.retries; i++) {
        state = samson.getDeploy("${deploy.deploy_ids[0]}");
        status = "[Samson] deploy ${deploy.deploy_ids[0]}: ${state.deploy.status}"
        
        if ("${state.deploy.status}" == "pending" || 
            "${state.deploy.status}" == "running") {
            echo "${status}"
            sleep conf.seconds
        } else if( "${state.deploy.status}" == "errored"){
            echo "${status}"
            return 1
        }
    }
    
    if ("${state.deploy.status}" == "succeeded") {
        echo "${status}"
        return 0
    } else {
        echo "${status}"
        return 1
    }
}

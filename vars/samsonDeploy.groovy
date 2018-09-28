import zendesk.samson.Deploy


def call(Map conf = [:]) {
    if (!conf.retries) { conf.retries = 3 }
    if (!conf.seconds) { conf.seconds = 60 }
    
    // init samson and create deploy
    def samson = new Deploy(this, conf.host, conf.token, conf.webhook, conf.msg);
    def deploy = samson.createDeploy();

    // determine status of samson deploy, with retries and sleep time
    def state, status
    for (int i = 0; i <conf.retries; i++) {
        state = samson.getDeploy("${deploy.deploy_ids[0]}");
        status = "Deploy ${deploy.deploy_ids[0]}: ${state.deploy.status}"
        
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

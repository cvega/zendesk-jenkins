package zendesk.samson


class Deploy implements Serializable {
    String body="";    
    String message="";    
    String url="";   
    Integer statusCode;    
    boolean failure = false;


    Deploy(steps, token, webhook, host) {
        this.host = host
        this.steps = steps
        this.token = token
        this.webhook = webhook
     }


    def private parseResponse(HttpURLConnection connection){    
        this.statusCode = connection.responseCode;    
        this.message = connection.responseMessage;    
        this.failure = false;
        
        if(statusCode == 200 || statusCode == 201){    
            this.body = connection.content.text;    
        }else{    
            this.failure = true;    
            this.body = connection.getErrorStream().text;    
        }  
    }    


    def private doGetHttpRequestWithJson(String requestUrl){    
        return doHttpRequestWithJson("", requestUrl, "GET");    
    } 


    def private doPostHttpRequestWithJson(String json, String requestUrl){    
        return doHttpRequestWithJson(json, requestUrl, "POST");    
    }    


    def private  doPutHttpRequestWithJson(String json, String requestUrl){    
        return doHttpRequestWithJson(json, requestUrl, "PUT");    
    }


    def private doHttpRequestWithJson(String json, String requestUrl, String verb){ 
        URL url = new URL(requestUrl);    
        HttpURLConnection connection = url.openConnection();    

        connection.setRequestMethod(verb);
        connection.setRequestProperty("Authorization", "Bearer ${this.token}");    
        connection.setRequestProperty("Content-Type", "application/json");   
        connection.doOutput = true;    

        if (json.length() > 0){
            def writer = new OutputStreamWriter(connection.outputStream);    
            writer.write(json);    
            writer.flush();    
            writer.close();    
        }

        connection.connect();    

        parseResponse(connection);    

        if(failure){    
            return false
        }    

        def jsonSlurper = new groovy.json.JsonSlurper();
        return jsonSlurper.parseText(body);    
    }


    def create(String branch, String commit, String msg){
        def json = "{\"deploy\":{\"branch\": \"${branch}\",\"commit\": {\"sha\":\"${commit}\",\"message\":\"${msg}\"}}}"
        def res = doPostHttpRequestWithJson(json, "http://${this.host}//integrations/generic/${this.webhook}");
        return res.success;
    }


    def get(String id, String repo){
        def res = doGetHttpRequestWithJson("http://${this.host}/projects/${repo}/deploys/${id}");
        return res.success;
    }
}

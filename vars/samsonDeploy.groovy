def call(Map config = [:]) {
  try {
   container('curl') {
     def ids = sh """curl \
       -d '{"deploy":{"branch":"master","commit":{"sha":"notarealsha","message":"hello!"}}}' \
       -H "Authorization: Basic 2a7da312649b74ad67197520534249637090166da1e9e858ed9179208fe030f7" \
       -H "Content-Type: application/json" \
       -X POST http://samson.zd-mini.com/integrations/generic/ | \
       jq '.deploy_ids[]' """  
   } 
  }
  catch (err) {
    throw ${err}
  }
}

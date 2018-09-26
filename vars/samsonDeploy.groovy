def call(Map config = [:]) {
  try {
   container('curl') {
      sh "curl google.com"
   } 
  }
  catch (err) {
    throw ${err}
  }
}

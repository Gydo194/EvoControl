/*
Evo2 HTTP server 2.1.1

 */
package Evo2;

import java.util.HashMap;

/**
 *
 * @author Gydo194
 */
    public class HttpResponse {
        String listenURL = "";
        String returnData = "200\n -Evo2";
        String response = "";
        int status = 200;
        HashMap<String,String> params = new HashMap<String,String>();
        
        
        
        boolean isSuccessfullyDone = false;
        public HttpResponse(String URL, String initialReturnData, int statusCode) {
            listenURL = URL;
            returnData = initialReturnData;
            status = statusCode;
            System.out.println("Evo2.HttpResponse: constructing new HTTP response");
        }
        public String getResponse() { //override this function for a call-back dynamic response
            StringBuffer resp = new StringBuffer();
            resp.append("HTTP/1.1 "+this.status+"\n");
            resp.append("Server: Evo2\n");
            resp.append("Cache-Control: No-Cache\n");
            resp.append("\n");
            resp.append(this.returnData);
            return resp.toString();
        }
        
        public String getListenURL() {
            return this.listenURL;
        }
        
        public int getResponseStatus() {
            return this.status;
        }
    
        public String getRawReturnData() {
            return this.returnData;
        }
        
        public String getParam(String id) {
            return params.get(id);
        }
        
        public void setParamMap(HashMap<String,String> map ) {
            params = map;
        }
        
       
}

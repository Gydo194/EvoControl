
package Evo2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.concurrent.Callable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gydo194
 */

public class Evo2 implements Runnable {
    
    
    public class QueryString {
        private String url = "";
        private HashMap<String,String> parameters = new HashMap<String,String>();
        
        public QueryString() {
            
        }
        public void setURL(String newURL) {
                this.url = newURL;
        }
        public void setParamMap(HashMap<String,String> map) {
            this.parameters = map;
        }
        public String getURL() {
            return this.url;
        }
        
        public String parseURL(String inURL) {
             return inURL.split("GET|POST|HEAD ")[1].split("HTTP/*./*")[0].split("\\?")[0].trim();
        }
        
        public HashMap<String,String> getParams(String URL) {
            HashMap<String,String> params = new HashMap<String,String>();
            if(!URL.contains("?")) { return params; };
            String parsedURL = URL.split("GET|POST|HEAD ")[1].split("HTTP/*./*")[0].split("\\?")[1].trim();
            //System.out.println("getparams: parsed URL:" + parsedURL);
            if(!parsedURL.contains("=")) { return params; }
            //System.out.println("getParams: passing, contains =.");
            //String paramString = parsedURL.split("\\?")[1];
            for(String ampersand_split : parsedURL.split("&")) {
                //System.out.println("Ampersand_split:"+ampersand_split);
                try {
                if(ampersand_split.contains("=")) {
                    String id = ampersand_split.split("=")[0];
                    String val = ampersand_split.split("=")[1];
                    params.put(id,val);
                    //System.out.println("ID:"+id+", VAL:"+val+", putting.");
                }
                }catch(ArrayIndexOutOfBoundsException e) {
                    System.out.println("getParams: ArrayIndexOutOfBoundsException.");
                    System.out.println("getParams: Cannot use param.");
                }
            }
            return params;
        }
        
        public void setParams(HashMap<String,String> newMap) {
            this.parameters = newMap;
        }
        
        public String getParam(String id) {
            if(parameters.containsKey(id)) {
                return parameters.get(id);
            }
            else {
                return "";
            }
        }
        
        public HashMap<String,String> getParamMap() {
            return this.parameters;
        }
       
    }
    
    public class HttpHeaders {
        HashMap<String,String> headers = new HashMap<String,String>();
        
        public HttpHeaders() {
            
        }
        
        public void addHeader(String header, String value) {
            headers.put(header,value);
        }
        public HashMap<String,String> getMap() {
            return headers;
        }
        public String getValue(String header) {
            if(headers.containsKey(header)) {
                return headers.get(header);
            }
            else
            {
                return "";
            }
        }
        
        
        
    }
  
   

    
    
    public Evo2(int listenPort) {
        this.port = listenPort;
    }
    
    public void addResponse(HttpResponse newResponse) {
        Responses.add(newResponse);
    }
    
    //define EVO's vars.
    private enum requestTypes { GET,POST };
    String input = "";
    ArrayList<HttpResponse> Responses = new ArrayList<HttpResponse>();
    public QueryString query = new QueryString();
    public HttpHeaders headers = new HttpHeaders();
    int port = 3000;
    requestTypes requestType = requestTypes.GET;
    boolean successfulResponseDone = false;           

    
    public void run() {
       
        while(true){
        try {
            //define session vars
            ServerSocket sock = new ServerSocket(port);
            Socket http = sock.accept();
            PrintWriter out = new PrintWriter(http.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
            //handle connection: enumerate request type and headers
            while( (input = in.readLine() ) != null) {
                if(input.contains(": ") && !input.contains("GET|POST|HEAD")) {
                    //HTTP header line
                    if(input.split(": ").length > 0) {
                        String[] header = input.split(": ");
                        headers.addHeader(header[0], header[1]);
                        //System.out.println("Adding to header db: '"+header[0]+"','"+header[1]+"'.");
                        break;
                    }
                    
                }
                else { //no HTTP header
                    if(input.contains("GET") || input.contains("POST") ) { //HTTP request line
                        query.setURL(query.parseURL(input)); //get HTTP request URL
                        query.setParams(query.getParams(input)); //get HTTP queryString
                        //System.out.println("parseurl:"+query.getURL());
                        if(input.contains("GET")) { requestType = requestTypes.GET; }
                        if(input.contains("POST")) { requestType = requestTypes.POST; }
                        //System.out.println("req type:"+requestType);
                        break;
                        
                    }
                    else
                    {
                        //Whatever. Probably POST data, tough not implemented yet.
                        System.out.println("Evo2: cannot handle input data:" + input);
                        break;
                    }
                
                
                }
            }
            //System.out.println("ENDING ENUMERATION WHILE LOOP"); //all data received.
            //respond to the request
            for(HttpResponse resp: Responses) {
                //System.out.println("Comparing '"+query.getURL()+"' to '"+resp.getListenURL()+"'.");
                if(query.getURL().equals(resp.listenURL)) {
                    //System.out.println("compare: TRUE");
                    resp.setParamMap(query.getParamMap());
                    out.println(resp.getResponse()); //run the getResponse function to get the String data, subclass and override the getResponse() function in HttpResponse.java for dynamic responses.
                    //System.out.println("resp.getResponse:"+resp.getResponse());
                    successfulResponseDone = true;
                    break;
                }
            }
            
            if(!successfulResponseDone) {
            //return 404
            out.println("HTTP/1.1 404 NOT FOUND");
            out.println("Server: Evo2");
            out.println();
            out.println("404 - Evo2");
            //end 404
            }
            
            successfulResponseDone = false;
            
            http.close();
            sock.close();
            
            
            
        } catch (Exception e) {
            System.out.println("Evo: Exception in main HTTP try-catch: " + e);
            e.printStackTrace();
        }
    }
    
  }
    
    
    
}


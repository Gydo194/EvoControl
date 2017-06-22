


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gydo194
 */

  package Evo2;

import java.util.HashMap;
  import java.util.concurrent.Callable;
  public class ExecuteOnRequest extends HttpResponse {
        String subProcess = null;
        String sessionSubProcess = null;
        HashMap<String,String> params = new HashMap<String,String>();
        public ExecuteOnRequest(String listenURL, String initialReturnData, int status, String shellCommand) {
            super(listenURL, initialReturnData, status);
            //System.out.println("Creating CallBackClass.");
            subProcess = shellCommand;
            System.out.println("Creating HTTP response (ExecuteOnRequest) with URL:"+listenURL);
            
        }
        
        
        public void setParamMap(HashMap<String,String> map ) {
            params = map;
        }
        
        
        
        @Override
        public String getResponse() {
            if(subProcess != null) {
                try {
                    sessionSubProcess = subProcess;
                    /*
                    if( sessionSubProcess.contains("%r") || params.containsKey("r") ) {
                        sessionSubProcess = subProcess.replaceAll("%r", params.get("r"));
                    
                    }
                    System.out.println("sessionSubProcess = " + sessionSubProcess);
                    System.out.println("Running sub process.");
                    Runtime.getRuntime().exec(sessionSubProcess);
                    */
                    
                    // if sessionsubprocess contains % then split by % and replace all occurences by correct params
                    System.out.println("sessionSubProcess = " + sessionSubProcess);
                    if(sessionSubProcess.contains("%")) {
                        System.out.println("CONTAINS");
                        while(sessionSubProcess.contains("%")) {
                            System.out.println("sessionSubProcess = " + sessionSubProcess);
                            String first = sessionSubProcess.split("%")[0];
                            String second = sessionSubProcess.split("%")[1];
                            System.out.println("first = " + first);
                            System.out.println("second = " + second);
                            
                            
                            if(params.containsKey(second)) {
                                System.out.println("Params contains key "+second);
                                sessionSubProcess = sessionSubProcess.replaceAll("%"+second+"%", params.get(second));
                                System.out.println("Replacing %"+second+"% with :"+params.get(second));
                            }
                            else {
                                sessionSubProcess = sessionSubProcess.replaceFirst("%"+second+"%","");
                                System.out.println("new sessionSubProcess = " + sessionSubProcess);
                                
                            }
                        }
                        
                        
                    }
                    
                   //subProcess = sessionSubProcess; 
                    System.out.println("MODIFIED sessionSubProcess = " + sessionSubProcess);
                    System.out.println("Executing.");
                    Runtime.getRuntime().exec(sessionSubProcess);
                    
                } catch (Exception e) {
                    System.out.println("Exception in Call-Back:");
                } 
                //subProcess = "/usr/bin/say 'not reset'";
            }
            else {
                System.out.println("CallScriptOnCallBack: Error: Call Back is null");
            }
            //return super.getResponse();
            String executedSubProcess = sessionSubProcess;
            sessionSubProcess = subProcess;
            return "HTTP/1.1 200\nServer: Evo2\nCache-Control: No-Cache\n\nExecuted:"+executedSubProcess;
        }
    }
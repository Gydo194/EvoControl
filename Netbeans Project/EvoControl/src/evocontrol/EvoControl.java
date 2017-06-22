/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evocontrol;
import Evo2.*;
import java.io.File;
import other.GetProperties;
/**
 *
 * @author gydokosten
 */
public class EvoControl {

    
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        String configFile = (new File(".").getAbsolutePath());
        configFile += "/EvoControl.conf";
        System.out.println("original configFile = " + configFile);
        int port = 8000;
        
        if(args.length > 0) {
        if(args[1] != null || args[0] != "" ) {
            port = Integer.parseInt(args[0]);
        }
        
        if(args.length >= 1) {
        if(args[1] != null || args[1] != "") {
            configFile = args[1];
            System.out.println("Setting configFile to:"+configFile);
        }
        else {
            System.out.println("USAGE: java -jar EvoControl.jar <config file> <port>");
        }
        } else {System.out.println("Not setting config file.");}
        } else {System.out.println("Not setting port.");}
        
        
        GetProperties prop = new GetProperties(configFile);
        
        System.out.println("URL property:" + prop.getProperty("URL"));
        
        Evo2 evoServer = new Evo2(port);
        Thread server_Thread = new Thread(evoServer);
        server_Thread.start(); //start the server in new thread
                
        for(String val: prop.getUrls()) {
           ExecuteOnRequest exec = new ExecuteOnRequest(prop.getUrls().get(prop.indexOf(val)), "ok",200,prop.getValues().get(prop.indexOf(val)));
           evoServer.addResponse(exec);
        }
        
    }
    
}

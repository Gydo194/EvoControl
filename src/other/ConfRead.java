package other;

/*
 Config reader 1.0beta-1 by Gydo Kosten
 10-4-17
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author gydokosten
 */
public class ConfRead {
    StringBuffer out;
    ArrayList<String> contents = new ArrayList<String>();
    public String fromJar(String filename){
        //StringBuffer out;
        try{
          InputStream in = getClass().getResourceAsStream(filename);
          InputStreamReader rd = new InputStreamReader(in);
          BufferedReader bf = new BufferedReader(rd);
          out = new StringBuffer();
          String inLine;
          while((inLine = bf.readLine()) != null){
              out.append(inLine);
          }
          in.close();
          bf.close();
          rd.close();
          //return out.toString();
            
            
        }
        //return out.toString();
        catch(IOException e){
            //derp.
        }
        return out.toString();
    }
    
    
    public ArrayList<String> listDb(){
        contents.clear();
        String db = fromJar("/Modules.db");
        System.out.println("DB:" + db);
        String[] spl = db.split(";");
   //     contents.clear();
        for(String i : spl){
            System.out.println("Printing i:");
           System.out.println(i);
           contents.add(i); 
           //contents.add("/Modules/playsound.js");
        }
        return contents;
        
    }
    public String[] getDb() {
        System.out.println("GETDB CALLED");
        return fromJar("/Modules.db").split(";");
    }
    
    
//    String output;
   
    public String fromDisk(String filename){
    String output = null;
        try {
           output = new Scanner(new URL("file://"+filename).openStream(),"UTF-8").useDelimiter("\\A").next();
            //System.out.println("FROMDISK READ:" + output);
        } catch (Exception e) {
            System.out.println("fromDisk caught exception:");
            e.printStackTrace();
        }
        
      return output;  
    }
    
    
    
    
    
}

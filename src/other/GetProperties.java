/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Gydo194
 */
public class GetProperties {
    HashMap<String,String> propMap = new HashMap<String,String>();
    ArrayList<String> values = new ArrayList<String>();
    ArrayList<String> urls = new ArrayList<String>();
    public GetProperties(String propertyString) {
        ConfRead cr = new ConfRead();
        String props = cr.fromDisk(propertyString);
        try {
        if(props.contains(";") || props.contains("=")) {
            for(String cur: props.split(";")) {
                System.out.println("cur.trim() = " + cur.trim());
                if(cur.contains("=") && cur.contains("url") && cur.contains("script") && cur.contains(",") && !cur.equals("")) {
                  String url = cur.split("url=")[1].split(",")[0].trim();
                  String script = cur.split("script=")[1].split(";")[0].trim(); //raw script including parameters
                    System.out.println("url = " + url);
                    System.out.println("script = " + script);
                    propMap.put(url, script);
                    values.add(script);
                    urls.add(url);
                    
                }
                else {
                    System.out.println("GetProperties: Syntax Error. Cannot parse input data.");
                }
                
            }
        }
        else {
            System.out.println("GetProperties: Syntax Error: Property file missing \"=\" or \";\". Cannot parse.");
        }
        
        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Array Exception");
            System.out.println("Cannot use parameter.");
        }
        
        
    }
    
    
    
    public String getProperty(String key) {
        if(propMap.containsKey(key)) {
            return propMap.get(key);
        }
        else {
            return "";
        }
    }
    
    public ArrayList<String> getValues() {
        return values;
    }
    public ArrayList<String> getUrls() {
        return urls;
    }
    
    public int indexOf(String url) {
        return urls.indexOf(url);
    }
    
    public int indexOfValue(String value) {
        return values.indexOf(value);
    }
    
    
}
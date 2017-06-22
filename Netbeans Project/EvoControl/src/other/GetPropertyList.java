/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import java.util.HashMap;

/**
 *
 * @author gydokosten
 */
public class GetPropertyList {
    HashMap<String,String> propMap = new HashMap<String,String>();
    public GetPropertyList(String propertyString) {
        ConfRead cr = new ConfRead();
        String props = cr.fromDisk(propertyString);
        
        if(props.contains(";") || props.contains("=")) {
            for(String cur: props.split(";")) {
                if(cur.contains("=")) {
                    String key = cur.split("=")[0].replaceAll("\n","");
                    String val = cur.split("=")[1].replaceAll("\n", "");
                    propMap.put(key, val);
                    System.out.println("GetProperties: Key:"+key+", val:"+val+". Putting.");
                }
                else {
                    System.out.println("GetProperties: Syntax Error: cannot split by =.");
                }
                
            }
        }
        else {
            System.out.println("GetProperties: Syntax Error: Property file missing \"=\" or \";\". Cannot parse.");
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
    
    
}

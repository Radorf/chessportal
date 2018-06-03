/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.util.exceptions;

/**
 *
 * @author E_Villodas
 */

@SuppressWarnings("serial")
public abstract class InstanceException extends Exception {

    private Object key;
    private String className;
    
    protected InstanceException(String specificMessage, Object key, 
        String className) {
        
        super(specificMessage + " (key = '" + key + "' - className = '" + 
            className + "')");
        this.key = key;
        this.className = className;
        
    }
    
    public Object getKey() {
        return key;
    }
    
    public String getClassName() {
        return className;
    }

}

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
public class DatesInconsistenceException extends InstanceException{
    public DatesInconsistenceException(Object key, String className){
        super("Dates inconsistency",key,className);
    }
}

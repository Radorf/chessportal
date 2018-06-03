/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.util.dao;


import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import java.io.Serializable;

/**
 *
 * @author E_Villodas
 */
public interface GenericDao <E, PK extends Serializable>{

	void save(E entity);

	E find(PK id) throws InstanceNotFoundException;

	void remove(PK id) throws InstanceNotFoundException;

}

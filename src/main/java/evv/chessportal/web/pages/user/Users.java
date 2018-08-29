/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.web.pages.user;

import evv.chessportal.model.userprofile.UserProfile;
import evv.chessportal.model.userservice.UserService;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author E_Villodas
 */

public class Users {

    @Inject
    private UserService userService;

    @Property
    @Persist(PersistenceConstants.FLASH)
    private String searchKey;

    @Property
    private ArrayList<UserProfile> userList;

    @Property
    UserProfile user;

     void setupRender() {
        userList = userService.searchByGeneralKey(searchKey);
    }
    
    Object onActionFromDeleteUser(Long id){           
        try {
            //TODO logged user should not be deleted
            userService.deleteUser(id);
        } catch (InstanceNotFoundException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
}

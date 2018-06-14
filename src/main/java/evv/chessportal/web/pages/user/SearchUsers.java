/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.web.pages.user;

import evv.chessportal.model.userprofile.UserProfile;
import evv.chessportal.model.userservice.UserService;
import java.util.ArrayList;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author E_Villodas
 */

public class SearchUsers {

    @Inject
    private UserService userService;

    @Property
    private String searchKey;

    @Property    
    @Persist(PersistenceConstants.FLASH)
    private ArrayList<UserProfile> userList;

    @Property
    UserProfile user;

    Object onSuccess() {
//        userList =  new ArrayList<UserProfile>();
        userList = userService.searchByGeneralKey(searchKey);
        return this;
    }
}

package evv.chessportal.web.pages.user;


import evv.chessportal.model.userprofile.UserProfile;
import evv.chessportal.model.userservice.UserService;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author E_Villodas
 */
public class ShowUserProfile {

    @Property
    UserProfile user;
    
    @Inject
    UserService userService;

    void onActivate(long userId) {
        try {
            user = userService.findUserProfile(userId);
        } catch (InstanceNotFoundException ex) {
           //TODO error page
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

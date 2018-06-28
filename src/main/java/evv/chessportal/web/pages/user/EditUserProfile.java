/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.web.pages.user;

import evv.chessportal.model.userprofile.UserProfile;
import evv.chessportal.model.userservice.PersonDetails;
import evv.chessportal.model.userservice.UserService;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author E_Villodas
 */
public class EditUserProfile {

    @Property
    private String firstName;

    @Property
    private String surName;

    @Property
    private String email;

    @Property
    private String phoneNumber;

    @Inject
    private UserService userService;

    @Property
    private UserProfile user;

    void onActivate(long userId) {
        try {
            user = userService.findUserProfile(userId);
        } catch (InstanceNotFoundException ex) {
            //TODO error page
            Logger.getLogger(SearchUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void onPrepareForRender() throws InstanceNotFoundException {
        
        firstName = user.getPerson().getFirstName();
        surName = user.getPerson().getSurname();
        email = user.getPerson().getEmail();
        phoneNumber = user.getPerson().getPhoneNumber();
    }

    Object onSuccess() throws InstanceNotFoundException {

        userService.updateUserProfileDetails(user
                .getId(), new PersonDetails(
                firstName, surName, email, phoneNumber));
        return ShowUserProfile.class;

    }
}

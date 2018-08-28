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
import evv.chessportal.web.util.UserSession;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author E_Villodas
 */
public class UpdateUser {

    @Property
    private String firstName;

    @Property
    private String surName;

    @Property
    private String email;

    @Property
    private String phoneNumber;

    @Property
    private Integer elo;

    @Property
    private String licenseNumber;

    @SessionState(create = false)
    private UserSession userSession;

    @Inject
    private UserService userService;

    void onPrepareForRender() throws InstanceNotFoundException {

        UserProfile userProfile;

        userProfile = userService.findUserProfile(userSession
                .getUserProfileId());
        firstName = userProfile.getPerson().getFirstName();
        surName = userProfile.getPerson().getSurname();
        email = userProfile.getPerson().getEmail();
        phoneNumber = userProfile.getPerson().getPhoneNumber();
    }

    Object onSuccess() throws InstanceNotFoundException {

        userService.updateUserProfileDetails(userSession.getUserProfileId(),elo,licenseNumber, new PersonDetails(
                firstName, surName, email, phoneNumber));
        userSession.setFirstName(firstName);
        return Users.class;

    }
}

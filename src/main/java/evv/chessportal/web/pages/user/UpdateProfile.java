package evv.chessportal.web.pages.user;

import evv.chessportal.model.userprofile.UserProfile;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import evv.chessportal.model.userservice.PersonDetails;
import evv.chessportal.model.userservice.UserService;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import evv.chessportal.web.pages.Index;
import evv.chessportal.web.services.AuthenticationPolicy;
import evv.chessportal.web.services.AuthenticationPolicyType;
import evv.chessportal.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class UpdateProfile {

    @Property
    private String firstName;

    @Property
    private String surName;

    @Property
    private String email;
    
    @Property
    private String phoneNumber;

    @SessionState(create = false)
    private UserSession userSession;

    @Inject
    private UserService userService;

    void onPrepareForRender() throws InstanceNotFoundException {

        UserProfile userProfile;

        userProfile = userService.findUserProfile(userSession
                .getUserProfileId());
        firstName = userProfile.getPerson().getFirstName();
        surName = userProfile.getPerson().getSurName();
        email = userProfile.getPerson().getEmail();
        phoneNumber = userProfile.getPerson().getPhoneNumber();
    }

    Object onSuccess() throws InstanceNotFoundException {

        userService.updateUserProfileDetails(userSession.getUserProfileId(), new PersonDetails(
                firstName, surName, email, phoneNumber));
        userSession.setFirstName(firstName);
        return Index.class;

    }

}

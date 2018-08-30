package evv.chessportal.web.pages.user;

import evv.chessportal.model.userprofile.UserProfile;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import evv.chessportal.model.userservice.PersonDetails;
import evv.chessportal.model.userservice.UserService;
import evv.chessportal.model.util.exceptions.DuplicateInstanceException;
import evv.chessportal.web.pages.Index;
import evv.chessportal.web.services.AuthenticationPolicy;
import evv.chessportal.web.services.AuthenticationPolicyType;
import evv.chessportal.web.util.UserSession;
import org.apache.tapestry5.beaneditor.Validate;

@AuthenticationPolicy(AuthenticationPolicyType.NON_AUTHENTICATED_USERS)
public class Register {

    @Property
    private String loginName;

    @Property
    private String password;

    @Property
    private String retypePassword;

    @Property
    private String firstName;

    @Property
    private String surName;

    @Property
    private String email;
    
    @Property
    private String phoneNumber;

    @SessionState(create=false)
    private UserSession userSession;

    @Inject
    private UserService userService;

    @Component
    private Form registrationForm;

    @Component(id = "loginName")
    private TextField loginNameField;

    @Component(id = "password")
    private PasswordField passwordField;

    @Inject
    private Messages messages;

    private Long userProfileId;

    void onValidateFromRegistrationForm() {

        if (!registrationForm.isValid()) {
            return;
        }

        if (!password.equals(retypePassword)) {
            registrationForm.recordError(passwordField, messages
                    .get("error-passwordsDontMatch"));
        }

    }

    Object onSuccess() {
        try {
            UserProfile userProfile = userService.registerPlayer(loginName, password,0,null,
                new PersonDetails(firstName, surName, email,phoneNumber));
            userProfileId = userProfile.getId();
            userSession = new UserSession(userProfile);
        } catch (DuplicateInstanceException e) {
            registrationForm.recordError(loginNameField, messages
                    .get("error-loginNameAlreadyExists"));
            return this;
        }
        return Index.class;

    }

}

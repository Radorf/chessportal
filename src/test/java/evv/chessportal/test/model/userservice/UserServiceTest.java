package evv.chessportal.test.model.userservice;

import static evv.chessportal.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static evv.chessportal.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import evv.chessportal.model.userprofile.UserProfile;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import evv.chessportal.model.userservice.IncorrectPasswordException;
import evv.chessportal.model.userservice.PersonDetails;
import evv.chessportal.model.userservice.UserService;
import evv.chessportal.model.util.exceptions.DuplicateInstanceException;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE})
@Transactional
public class UserServiceTest {

    private final long NON_EXISTENT_USER_PROFILE_ID = -1;

    @Autowired
    private UserService userService;

    @Test
    public void testRegisterUserAndFindUserProfile()
            throws DuplicateInstanceException, InstanceNotFoundException {

        /* Register user and find profile. */
        UserProfile userProfile = userService.registerUser("user", "userPassword",
                new PersonDetails("name", "lastName", "user@udc.es", "981123456"));

        UserProfile userProfile2 = userService.findUserProfile(
                userProfile.getId());

        /* Check data. */
        assertEquals(userProfile, userProfile2);

    }

    @Test(expected = DuplicateInstanceException.class)
    public void testRegisterDuplicatedUser() throws DuplicateInstanceException,
            InstanceNotFoundException {

        String loginName = "user";
        String clearPassword = "userPassword";
        PersonDetails userProfileDetails = new PersonDetails(
                "name", "lastName", "user@udc.es", "981123456");

        userService.registerUser(loginName, clearPassword,
                userProfileDetails);

        userService.registerUser(loginName, clearPassword,
                userProfileDetails);

    }

    @Test
    public void testLoginClearPassword() throws IncorrectPasswordException,
            InstanceNotFoundException {

        String clearPassword = "userPassword";
        UserProfile userProfile = registerUser("user", clearPassword);

        UserProfile userProfile2 = userService.login(userProfile.getLoginName(),
                clearPassword, false);

        assertEquals(userProfile, userProfile2);

    }

    @Test
    public void testLoginEncryptedPassword() throws IncorrectPasswordException,
            InstanceNotFoundException {

        UserProfile userProfile = registerUser("user", "clearPassword");

        UserProfile userProfile2 = userService.login(userProfile.getLoginName(),
                userProfile.getEncryptedPassword(), true);

        assertEquals(userProfile, userProfile2);

    }

    @Test(expected = IncorrectPasswordException.class)
    public void testLoginIncorrectPasword() throws IncorrectPasswordException,
            InstanceNotFoundException {

        String clearPassword = "userPassword";
        UserProfile userProfile = registerUser("user", clearPassword);

        userService.login(userProfile.getLoginName(), 'X' + clearPassword,
                false);

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testLoginWithNonExistentUser()
            throws IncorrectPasswordException, InstanceNotFoundException {

        userService.login("user", "userPassword", false);

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testFindNonExistentUser() throws InstanceNotFoundException {

        userService.findUserProfile(NON_EXISTENT_USER_PROFILE_ID);

    }

    @Test
    public void testUpdate() throws InstanceNotFoundException,
            IncorrectPasswordException {

        /* Update profile. */
        String clearPassword = "userPassword";
        UserProfile userProfile = registerUser("user", clearPassword);

        PersonDetails newPersonDetails = new PersonDetails(
                'X' + userProfile.getPerson().getFirstName(), 'X' + userProfile.getPerson().getSurname(),
                'X' + userProfile.getPerson().getEmail(), userProfile.getPerson().getPhoneNumber());

        userService.updateUserProfileDetails(userProfile.getId(),
                newPersonDetails);

        /* Check changes. */
        userService.login(userProfile.getLoginName(), clearPassword, false);
        UserProfile userProfile2 = userService.findUserProfile(
                userProfile.getId());

        assertEquals(newPersonDetails.getFirstName(),
                userProfile2.getPerson().getFirstName());
        assertEquals(newPersonDetails.getSurName(),
                userProfile2.getPerson().getSurname());
        assertEquals(newPersonDetails.getEmail(),
                userProfile2.getPerson().getEmail());
        assertEquals(newPersonDetails.getPhoneNumber(),
                userProfile2.getPerson().getPhoneNumber());

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testUpdateWithNonExistentUser()
            throws InstanceNotFoundException {

        userService.updateUserProfileDetails(NON_EXISTENT_USER_PROFILE_ID,
                new PersonDetails("name", "lastName", "user@udc.es","981123456"));

    }

    @Test
    public void testChangePassword() throws InstanceNotFoundException,
            IncorrectPasswordException {

        /* Change password. */
        String clearPassword = "userPassword";
        UserProfile userProfile = registerUser("user", clearPassword);
        String newClearPassword = 'X' + clearPassword;

        userService.changePassword(userProfile.getId(),
                clearPassword, newClearPassword);

        /* Check new password. */
        userService.login(userProfile.getLoginName(), newClearPassword, false);

    }

    @Test(expected = IncorrectPasswordException.class)
    public void testChangePasswordWithIncorrectPassword()
            throws InstanceNotFoundException, IncorrectPasswordException {

        String clearPassword = "userPassword";
        UserProfile userProfile = registerUser("user", clearPassword);

        userService.changePassword(userProfile.getId(),
                'X' + clearPassword, 'Y' + clearPassword);

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testChangePasswordWithNonExistentUser()
            throws InstanceNotFoundException, IncorrectPasswordException {

        userService.changePassword(NON_EXISTENT_USER_PROFILE_ID,
                "userPassword", "XuserPassword");

    }

    private UserProfile registerUser(String loginName, String clearPassword) {

        PersonDetails userProfileDetails = new PersonDetails(
                "name", "lastName", "user@udc.es","981123456");

        try {

            return userService.registerUser(
                    loginName, clearPassword, userProfileDetails);

        } catch (DuplicateInstanceException e) {
            throw new RuntimeException(e);
        }

    }

}

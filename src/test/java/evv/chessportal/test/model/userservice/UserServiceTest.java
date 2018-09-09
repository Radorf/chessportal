package evv.chessportal.test.model.userservice;

import evv.chessportal.model.game.Game;
import evv.chessportal.model.individualround.IndividualRound;
import evv.chessportal.model.individualtournament.IndividualTournament;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.model.userprofile.UserProfile;
import evv.chessportal.model.userservice.IncorrectPasswordException;
import static evv.chessportal.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static evv.chessportal.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import evv.chessportal.model.userservice.PersonDetails;
import evv.chessportal.model.userservice.UserService;
import evv.chessportal.model.util.exceptions.DatesInconsistenceException;
import evv.chessportal.model.util.exceptions.DuplicateInstanceException;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE})
@Transactional
public class UserServiceTest {

    private final long NON_EXISTENT_USER_PROFILE_ID = -1;

    @Autowired
    private UserService userService;
     @Autowired
    private TournamentService tournamentService;

    @Test
    public void testCompile(){
        assertEquals(100,100);
    }
     @Test
    public void generateRRIndividualTournamentRoundstest() throws DatesInconsistenceException, DuplicateInstanceException, InstanceNotFoundException{
        int n = 8;
        IndividualTournament individualTournament = tournamentService.createRRIndividualTournament("TestTournament", Calendar.getInstance(),
                null, null, null);
        ArrayList<Long> playerIds=new ArrayList<>(n);
        for(int i = 0;i<n;i++){
             playerIds.add(i,userService.createUser("jugador"+ (i+1), "admin", null, null, new PersonDetails(null, null, null, null), false).getId());
        }
        tournamentService.enrolPlayers(individualTournament.getId(), playerIds);
        individualTournament=tournamentService.generateRRIndividualTournamentRounds(individualTournament.getId());
        
        List<IndividualRound> rounds = individualTournament.getRoundList() ;
        for(IndividualRound r: rounds){
            System.out.println("Round " + r.getNumber_());
            List<Game> games = r.getGameList();
            for (Game g: games){
                System.out.println(g.getWhitePiecesPlayer().getLoginName()+" - " + g.getBlackPiecesPlayer().getLoginName());
            }
        }
        assertEquals(100,100);
    }
    @Test
    public void testRegisterUserAndFindPlayerProfile()
            throws DuplicateInstanceException, InstanceNotFoundException {

        /* Register user and find profile. */
        UserProfile userProfile = userService.registerPlayer("user", "userPassword",1600,"1254896",
                new PersonDetails("name", "lastName", "user@udc.es", "981123456"));

        UserProfile userProfile2 = userService.findUserProfile(
                userProfile.getId());

        /* Check data. */
        assertEquals(userProfile, userProfile2);

    }

    @Test(expected = DuplicateInstanceException.class)
    public void testRegisterDuplicatedPlayer() throws DuplicateInstanceException,
            InstanceNotFoundException {

        String loginName = "user";
        String clearPassword = "userPassword";
        PersonDetails userProfileDetails = new PersonDetails(
                "name", "lastName", "user@udc.es", "981123456");

        userService.registerPlayer(loginName, clearPassword,1600,"1254896",
                userProfileDetails);

        userService.registerPlayer(loginName, clearPassword,1600,"1254896",
                userProfileDetails);

    }

    @Test
    public void testLoginClearPassword() throws IncorrectPasswordException,
            InstanceNotFoundException {

        String clearPassword = "userPassword";
        UserProfile userProfile = registerPlayer("user", clearPassword);

        UserProfile userProfile2 = userService.login(userProfile.getLoginName(),
                clearPassword, false);

        assertEquals(userProfile, userProfile2);

    }

    @Test
    public void testLoginEncryptedPassword() throws IncorrectPasswordException,
            InstanceNotFoundException {

        UserProfile userProfile = registerPlayer("user", "clearPassword");

        UserProfile userProfile2 = userService.login(userProfile.getLoginName(),
                userProfile.getEncryptedPassword(), true);

        assertEquals(userProfile, userProfile2);

    }

    @Test(expected = IncorrectPasswordException.class)
    public void testLoginIncorrectPasword() throws IncorrectPasswordException,
            InstanceNotFoundException {

        String clearPassword = "userPassword";
        UserProfile userProfile = registerPlayer("user", clearPassword);

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
        UserProfile userProfile = registerPlayer("user", clearPassword);

        PersonDetails newPersonDetails = new PersonDetails(
                'X' + userProfile.getPerson().getFirstName(), 'X' + userProfile.getPerson().getSurname(),
                'X' + userProfile.getPerson().getEmail(), userProfile.getPerson().getPhoneNumber());

        userService.updateUserProfileDetails(userProfile.getId(),null,null,
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

        userService.updateUserProfileDetails(NON_EXISTENT_USER_PROFILE_ID,null,null,
                new PersonDetails("name", "lastName", "user@udc.es","981123456"));

    }

    @Test
    public void testChangePassword() throws InstanceNotFoundException,
            IncorrectPasswordException {

        /* Change password. */
        String clearPassword = "userPassword";
        UserProfile userProfile = registerPlayer("user", clearPassword);
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
        UserProfile userProfile = registerPlayer("user", clearPassword);

        userService.changePassword(userProfile.getId(),
                'X' + clearPassword, 'Y' + clearPassword);

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testChangePasswordWithNonExistentUser()
            throws InstanceNotFoundException, IncorrectPasswordException {

        userService.changePassword(NON_EXISTENT_USER_PROFILE_ID,
                "userPassword", "XuserPassword");

    }

    private UserProfile registerPlayer(String loginName, String clearPassword) {

        PersonDetails userProfileDetails = new PersonDetails(
                "name", "lastName", "user@udc.es","981123456");

        try {

            return userService.registerPlayer(
                    loginName, clearPassword, 1600,"1254896",userProfileDetails);

        } catch (DuplicateInstanceException e) {
            throw new RuntimeException(e);
        }

    }

}

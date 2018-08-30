package evv.chessportal.model.userservice;

import evv.chessportal.model.player.Player;
import evv.chessportal.model.userprofile.UserProfile;
import evv.chessportal.model.util.exceptions.DuplicateInstanceException;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import java.util.ArrayList;

public interface UserService {

    /**
     * Registers a Player in the application
     * @throws DuplicateInstanceException Username already exists.
     */
    public Player registerPlayer(String loginName, String clearPassword, Integer elo, String licenseNumber, PersonDetails userProfileDetails)
            throws DuplicateInstanceException;

    
    public UserProfile createUser(String loginName, String clearPassword,Integer elo,            
            String licenseNumber, PersonDetails personDetails,boolean isAdmin)
            throws DuplicateInstanceException;
    
    /**
     * Look for the loginName in the application and compares the password with the one stored.

     * @return
     * @throws InstanceNotFoundException
     * @throws IncorrectPasswordException 
     */
    public UserProfile login(String loginName, String password,
            boolean passwordIsEncrypted) throws InstanceNotFoundException,
            IncorrectPasswordException;

    public UserProfile findUserProfile(Long userProfileId)
            throws InstanceNotFoundException;

    public void updateUserProfileDetails(Long userProfileId,Integer elo,            
            String licenseNumber, PersonDetails userProfileDetails)
            throws InstanceNotFoundException;

    public void changePassword(Long userProfileId, String oldClearPassword,
            String newClearPassword) throws IncorrectPasswordException,
            InstanceNotFoundException;

    public ArrayList<UserProfile> searchByGeneralKey(String searchKey);

    public void deleteUser(Long userProfileId) throws InstanceNotFoundException;

    public ArrayList<UserProfile> searchAll();
    
}

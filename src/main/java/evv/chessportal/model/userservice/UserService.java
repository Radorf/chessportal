package evv.chessportal.model.userservice;

import evv.chessportal.model.userprofile.UserProfile;
import evv.chessportal.model.util.exceptions.DuplicateInstanceException;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;

public interface UserService {

    public UserProfile registerUser(String loginName, String clearPassword,
            PersonDetails userProfileDetails)
            throws DuplicateInstanceException;

    public UserProfile login(String loginName, String password,
            boolean passwordIsEncrypted) throws InstanceNotFoundException,
            IncorrectPasswordException;

    public UserProfile findUserProfile(Long userProfileId)
            throws InstanceNotFoundException;

    public void updateUserProfileDetails(Long userProfileId,
            PersonDetails userProfileDetails)
            throws InstanceNotFoundException;

    public void changePassword(Long userProfileId, String oldClearPassword,
            String newClearPassword) throws IncorrectPasswordException,
            InstanceNotFoundException;

}

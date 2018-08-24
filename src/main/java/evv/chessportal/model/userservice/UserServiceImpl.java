package evv.chessportal.model.userservice;

import evv.chessportal.model.person.Person;
import evv.chessportal.model.userprofile.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import evv.chessportal.model.userprofile.UserProfileDao;
import evv.chessportal.model.userservice.util.PasswordEncrypter;
import evv.chessportal.model.util.exceptions.DuplicateInstanceException;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import java.util.ArrayList;


@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserProfileDao userProfileDao;

    public void setUserProfileDao(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    

    @Override
    public UserProfile registerUser(String loginName, String clearPassword,
            PersonDetails personDetails)
            throws DuplicateInstanceException {

        try {
            userProfileDao.findByLoginName(loginName);
            throw new DuplicateInstanceException(loginName,
                    UserProfile.class.getName());
        } catch (InstanceNotFoundException e) {
            String encryptedPassword = PasswordEncrypter.crypt(clearPassword);
            Person person = new Person(personDetails.getFirstName(), personDetails.getSurName(), personDetails.getEmail(),
                    personDetails.getPhoneNumber());
            UserProfile userProfile = new UserProfile(loginName,
                    encryptedPassword, person);

            userProfileDao.save(userProfile);
            return userProfile;
        }

    }

    @Transactional(readOnly = true)
    @Override
    public UserProfile login(String loginName, String password,
            boolean passwordIsEncrypted) throws InstanceNotFoundException,
            IncorrectPasswordException {

        UserProfile userProfile = userProfileDao.findByLoginName(loginName);
        String storedPassword = userProfile.getEncryptedPassword();

        PasswordEncrypter.crypt(password);
        if (passwordIsEncrypted) {
            if (!password.equals(storedPassword)) {
                throw new IncorrectPasswordException(loginName);
            }
        } else if (!PasswordEncrypter.isClearPasswordCorrect(password,
                storedPassword)) {
            throw new IncorrectPasswordException(loginName);
        }
        return userProfile;

    }

    @Transactional(readOnly = true)
    @Override
    public UserProfile findUserProfile(Long userProfileId)
            throws InstanceNotFoundException {

        return userProfileDao.find(userProfileId);
    }

    @Override
    public void updateUserProfileDetails(Long userProfileId,
            PersonDetails personDetails)
            throws InstanceNotFoundException {
        UserProfile userProfile = userProfileDao.find(userProfileId);
        Person person = userProfile.getPerson();
        person.setFirstName(personDetails.getFirstName());
        person.setSurname(personDetails.getSurName());
        person.setEmail(personDetails.getEmail());
        person.setPhoneNumber(personDetails.getPhoneNumber());
        userProfile.setPerson(person);
    }

    @Override
    public void changePassword(Long userProfileId, String oldClearPassword,
            String newClearPassword) throws IncorrectPasswordException,
            InstanceNotFoundException {

        UserProfile userProfile;
        userProfile = userProfileDao.find(userProfileId);

        String storedPassword = userProfile.getEncryptedPassword();

        if (!PasswordEncrypter.isClearPasswordCorrect(oldClearPassword,
                storedPassword)) {
            throw new IncorrectPasswordException(userProfile.getLoginName());
        }

        userProfile.setEncryptedPassword(PasswordEncrypter
                .crypt(newClearPassword));

    }

    @Override
    public ArrayList<UserProfile> searchByGeneralKey(String searchKey) {
        return userProfileDao.searchByGeneralKey(searchKey);
    }

    @Override
    public void deleteUser(Long userProfileId) throws InstanceNotFoundException {        
         userProfileDao.find(userProfileId);
         userProfileDao.remove(userProfileId);
    }

}

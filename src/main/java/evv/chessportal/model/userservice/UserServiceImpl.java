package evv.chessportal.model.userservice;

import evv.chessportal.model.administrator.Administrator;
import evv.chessportal.model.administrator.AdministratorDao;
import evv.chessportal.model.person.Person;
import evv.chessportal.model.player.Player;
import evv.chessportal.model.player.PlayerDao;
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

    @Autowired
    private AdministratorDao administratorDao;

    @Autowired
    private PlayerDao playerDao;

    public void setUserProfileDao(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    public void setAdministratorDao(AdministratorDao administratorDao) {
        this.administratorDao = administratorDao;
    }

    public void setPlayerDao(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public Player registerPlayer(String loginName, String clearPassword, Integer elo, String licenseNumber, PersonDetails personDetails)
            throws DuplicateInstanceException {

        try {
            userProfileDao.findByLoginName(loginName);
            throw new DuplicateInstanceException(loginName,
                    UserProfile.class.getName());
        } catch (InstanceNotFoundException e) {
            String encryptedPassword = PasswordEncrypter.crypt(clearPassword);
            Person person = new Person(personDetails.getFirstName(), personDetails.getSurName(), personDetails.getEmail(),
                    personDetails.getPhoneNumber());
            Player player = new Player(loginName,
                    encryptedPassword, person, elo, licenseNumber);

            userProfileDao.save(player);
            return player;
        }

    }

    @Override
    public UserProfile createUser(String loginName, String clearPassword, Integer elo,
            String licenseNumber, PersonDetails personDetails, boolean isAdmin)
            throws DuplicateInstanceException {
        
        try {
            userProfileDao.findByLoginName(loginName);
            throw new DuplicateInstanceException(loginName,
                    UserProfile.class.getName());
        } catch (InstanceNotFoundException e) {
            String encryptedPassword = PasswordEncrypter.crypt(clearPassword);
            Person person = new Person(personDetails.getFirstName(), personDetails.getSurName(), personDetails.getEmail(),
                    personDetails.getPhoneNumber());
            if (isAdmin) {
                Administrator userProfile = new Administrator(loginName,encryptedPassword, person);
                administratorDao.save(userProfile);
                return userProfile;
            } else {
                Player userProfile = new Player(loginName, clearPassword, person, elo, licenseNumber);
                playerDao.save(userProfile);
                return userProfile;
            }
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
    public void updateUserProfileDetails(Long userProfileId, Integer elo,
            String licenseNumber, PersonDetails personDetails)
            throws InstanceNotFoundException {
        UserProfile userProfile = userProfileDao.find(userProfileId);
        if (userProfile instanceof Player) {
            ((Player) userProfile).setElo(elo);
            ((Player) userProfile).setLicenseNumber(licenseNumber);
        }
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

    @Override
    public ArrayList<UserProfile> searchAll() {
        return userProfileDao.searchAll();
    }

}

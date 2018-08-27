/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.userprofile;

import evv.chessportal.model.person.Person;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author E_Villodas
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "userType", discriminatorType = DiscriminatorType.STRING)
public class UserProfile {

    public final static String ADMIN_TYPE_STRING = "admin";
    public final static String PLAYER_TYPE_STRING = "player";

    public UserProfile() {
    }

    private Long id;
    private String encryptedPassword;

    private String loginName;

    private Person person;
    
    private Integer elo;
    
    private String licenseNumber;

    public UserProfile(String loginName, String password, Person person) {
        this.loginName = loginName;
        this.encryptedPassword = password;
        this.person = person;
    }
    
     public UserProfile(String loginName, String password, Person person, Integer elo,            
            String licenseNumber) {
        this.loginName = loginName;
        this.encryptedPassword = password;
        this.person = person;
        this.elo = elo;
        this.licenseNumber = licenseNumber;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.REMOVE})
    @JoinColumn(name = "idPerson")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Integer getElo() {
        return elo;
    }

    public void setElo(Integer elo) {
        this.elo = elo;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    
    @SequenceGenerator( // It only takes effect for
            name = "idUserProfileGenerator", // databases providing identifier
            sequenceName = "UserProfileGeneratorSeq") // generators.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idUserProfileGenerator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

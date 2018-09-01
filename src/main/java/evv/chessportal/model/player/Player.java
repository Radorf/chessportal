/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.player;

import evv.chessportal.model.person.Person;
import evv.chessportal.model.userprofile.UserProfile;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author E_Villodas
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("player")
public class Player extends UserProfile {

    public Player() {
    }

    public Player(String loginName, String password, Person person, Integer elo,
            String licenseNumber) {
        super(loginName, password, person);
        this.licenseNumber = licenseNumber;
    }

    private Integer elo;
    private String licenseNumber;

    /**
     *  This constructor purpose is to create a fake player in order to make the pairings in 
     * TournamentService.generateRRIndividualTournamentRounds()
     * @param jugadorFake
     */
    public Player(String jugadorFake) {
       super.setLoginName(jugadorFake);
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

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.individualtournament;

import evv.chessportal.model.player.Player;
import evv.chessportal.model.individualround.IndividualRound;
import evv.chessportal.model.tournament.Tournament;
import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author E_Villodas
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("individualTournament")
public class IndividualTournament extends Tournament {

    public IndividualTournament() {
    }
    private Set<Player> playerList;
    private Collection<IndividualRound> roundList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "individualTournament_player", joinColumns = @JoinColumn(name = "idTournament"), inverseJoinColumns = @JoinColumn(name = "idPlayer"))
    public Set<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(Set<Player> playerList) {
        this.playerList = playerList;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "individualTournament_individualRound", joinColumns = @JoinColumn(name = "idTournament"), inverseJoinColumns = @JoinColumn(name = "idRound"))
    public Collection<IndividualRound> getRoundList() {
        return roundList;
    }

    public void setRoundList(Collection<IndividualRound> roundList) {
        this.roundList = roundList;
    }

}

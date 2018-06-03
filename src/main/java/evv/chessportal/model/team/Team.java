/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.team;

import evv.chessportal.model.player.Player;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author E_Villodas
 */
@Entity
public class Team {

    public Team() {
    }

    private Long id;
    private String name_;
    private Collection<Player> captainList;
    private Collection<Player> playerList;

    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }

    @ManyToMany
    @JoinTable(name = "team_captainPlayer", joinColumns = @JoinColumn(name = "idTeam"),
            inverseJoinColumns = @JoinColumn(name = "idPlayer"))
    public Collection<Player> getCaptainList() {
        return captainList;
    }

    public void setCaptainList(Collection<Player> captainList) {
        this.captainList = captainList;
    }

    @ManyToMany
    @JoinTable(name = "team_player", joinColumns = @JoinColumn(name = "idTeam"),
            inverseJoinColumns = @JoinColumn(name = "idPlayer"))
    public Collection<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(Collection<Player> playerList) {
        this.playerList = playerList;
    }

    @SequenceGenerator( // It only takes effect for
            name = "idTeamGenerator", // databases providing identifier
            sequenceName = "TeamGeneratorSeq") // generators.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idTeamGenerator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

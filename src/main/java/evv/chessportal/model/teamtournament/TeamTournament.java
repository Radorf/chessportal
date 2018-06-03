/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.teamtournament;

import evv.chessportal.model.team.Team;
import evv.chessportal.model.teamround.TeamRound;
import evv.chessportal.model.tournament.Tournament;
import java.util.Collection;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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
@DiscriminatorValue("teamTournament")
public class TeamTournament extends Tournament {

    public TeamTournament() {
    }

    private Collection<Team> teamList;
    private Collection<TeamRound> roundList;

    @ManyToMany
    @JoinTable(name = "teamTournament_team", joinColumns = @JoinColumn(name = "idTournament"), inverseJoinColumns = @JoinColumn(name = "idTeam"))
    public Collection<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(Collection<Team> teamList) {
        this.teamList = teamList;
    }

    @ManyToMany
    @JoinTable(name = "teamTournament_teamRound", joinColumns = @JoinColumn(name = "idTournament"), inverseJoinColumns = @JoinColumn(name = "idRound"))
    public Collection<TeamRound> getRoundList() {
        return roundList;
    }

    public void setRoundList(Collection<TeamRound> roundList) {
        this.roundList = roundList;
    }
}

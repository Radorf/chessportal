/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.teammatch;

import evv.chessportal.model.team.Team;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author E_Villodas
 */
@Entity
public class TeamMatch {

    public TeamMatch() {
    }

    private Long id;
    private Float homeTeamPoints;
    private Float awayTeamPoints;

    private Team awayTeam;

    private Team homeTeam;

    public Float getHomeTeamPoints() {
        return homeTeamPoints;
    }

    public void setHomeTeamPoints(Float homeTeamPoints) {
        this.homeTeamPoints = homeTeamPoints;
    }

    public Float getAwayTeamPoints() {
        return awayTeamPoints;
    }

    public void setAwayTeamPoints(Float awayTeamPoints) {
        this.awayTeamPoints = awayTeamPoints;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAwayTeam")
    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idHomeTeam")
    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    @SequenceGenerator( // It only takes effect for
            name = "idTeamMatchGenerator", // databases providing identifier
            sequenceName = "TeamMatchGeneratorSeq") // generators.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idTeamMatchGenerator")
    public Long getId() {
        return id;
    }

    public void setId(Long idMatch) {
        this.id = idMatch;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.teamround;

import evv.chessportal.model.teamtournament.TeamTournament;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author E_Villodas
 */
@Entity
public class TeamRound {

    public TeamRound() {
    }

    private Long id;
    private Integer number_;
    private Calendar date_;

    private TeamTournament tournament;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idTournament")
    public TeamTournament getTournament() {
        return tournament;
    }

    public void setTournament(TeamTournament tournament) {
        this.tournament = tournament;
    }

    public Integer getNumber_() {
        return number_;
    }

    public void setNumber_(Integer number_) {
        this.number_ = number_;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Calendar getDate_() {
        return date_;
    }

    public void setDate_(Calendar date_) {
        this.date_ = date_;
    }

    @SequenceGenerator( // It only takes effect for
            name = "idTeamRound", // databases providing identifier
            sequenceName = "TeamRoundSeq") // generators.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idTeamRound")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

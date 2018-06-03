/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.tournament;

import java.util.Calendar;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author E_Villodas
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tournamentType", discriminatorType = DiscriminatorType.STRING)
public class Tournament {

    public final static String TEAM_TOURNAMENT_TYPE_STRING = "teamTournament";
    public final static String INDIVIDUAL_TOURNAMENT_TYPE_STRING = "individualTournament";

    public Tournament() {
    }

    private Long id;
    private String name_;
    private Calendar startDate;
    private Calendar endDate;
    private Calendar startEnrolmentDate;
    private Calendar endEnrolmentDate;

    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }

    @Temporal(TemporalType.DATE)
    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.DATE)
    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    @Temporal(TemporalType.DATE)
    public Calendar getStartEnrolmentDate() {
        return startEnrolmentDate;
    }

    public void setStartEnrolmentDate(Calendar startEnrolmentDate) {
        this.startEnrolmentDate = startEnrolmentDate;
    }

    @Temporal(TemporalType.DATE)
    public Calendar getEndEnrolmentDate() {
        return endEnrolmentDate;
    }

    public void setEndEnrolmentDate(Calendar endEnrolmentDate) {
        this.endEnrolmentDate = endEnrolmentDate;
    }

    @SequenceGenerator( // It only takes effect for
            name = "idTournamentGenerator", // databases providing identifier
            sequenceName = "iTournamentGeneratorSeq") // generators.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "idTournamentGenerator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

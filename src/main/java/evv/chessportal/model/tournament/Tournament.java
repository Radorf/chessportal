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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import evv.chessportal.model.tournamentservice.DraftState;
import evv.chessportal.model.tournamentservice.FinishedState;
import evv.chessportal.model.tournamentservice.StartedState;
import evv.chessportal.model.tournamentservice.TournamentState;

/**
 *
 * @author E_Villodas
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tournamentType", discriminatorType = DiscriminatorType.STRING)
@Where(clause = "deleted = 0")
public class Tournament {

    public final static String TEAM_TOURNAMENT_TYPE_STRING = "teamTournament";
    public final static String INDIVIDUAL_TOURNAMENT_TYPE_STRING = "individualTournament";

    public enum TournamentPairingsType {
        ROUND_ROBIN, SWISS
    }
    
    public enum Status {
        DRAFT, STARTED, FINISHED
    }
    
    public Tournament() {
        status=Status.DRAFT;
    }

    private Long id;
    private String name_;
    private Calendar startDate;
    private Calendar endDate;
    private Calendar startEnrolmentDate;
    private Calendar endEnrolmentDate;
    private TournamentPairingsType pairingsType;
    private Status status;
    private boolean deleted;

    @Enumerated(EnumType.STRING)
    public TournamentPairingsType getPairingsType() {
        return pairingsType;
    }

    public void setPairingsType(TournamentPairingsType pairingsType) {
        this.pairingsType = pairingsType;
    }

    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

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

    @Transient
    public TournamentState getState() {
        switch (status) {
        case DRAFT:
            return new DraftState();
        case STARTED:
            return new StartedState();
        case FINISHED:
            return new FinishedState();
        }
        return null;
    }

    public void setState(TournamentState state) {
        if (state instanceof DraftState) {
            this.status = Status.DRAFT;
        } else if (state instanceof StartedState) {
            this.status = Status.STARTED;
        } else if (state instanceof FinishedState) {
            this.status = Status.FINISHED;
        }
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    
}

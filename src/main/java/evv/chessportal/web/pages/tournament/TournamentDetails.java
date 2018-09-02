/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.web.pages.tournament;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import evv.chessportal.model.individualtournament.IndividualTournament;
import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.model.tournamentservice.TournamentState;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import evv.chessportal.web.services.AuthenticationPolicy;
import evv.chessportal.web.services.AuthenticationPolicyType;
import evv.chessportal.web.util.DateManagerUtil;

/**
 *
 * @author E_Villodas
 */
@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class TournamentDetails {

    @Property
    private Tournament tournament;
    
    @Property
    private Long tournamentId;
    
    @Inject
    private TournamentService tournamentService;
    
    @Inject
    private Messages messages;
    
    @Property
    private boolean canStart;
    
    @Property
    private boolean canShowPlayers;
    
    @Property
    private boolean canShowGames;
    
    @Property
    private boolean canEnrolPlayers;
    
    @Property
    private boolean canEdit;
    
    @Property
    private boolean canFinish;
    
    void onActivate(Long tournamentId){
        this.tournamentId=tournamentId;
    }

    Long onPassivate() {
        return tournamentId;
    }
    
    void setupRender() {
        try {
            tournament = tournamentService.findIndividualTournament(tournamentId);
            TournamentState state = tournament.getState();
            canStart = state.canStart(tournament, tournamentService);
            canShowPlayers = state.canShowPlayers(tournament, tournamentService);
            canShowGames = state.canShowGames(tournament, tournamentService);
            canEdit = state.canEnrolPlayers(tournament, tournamentService);
            canFinish = state.canFinish(tournament, tournamentService);
        } catch (InstanceNotFoundException ex) {
            //TODO error page
            Logger.getLogger(TournamentDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    Object onActionFromStartTournament(Long id) throws InstanceNotFoundException{
        tournament = tournamentService.findIndividualTournament(tournamentId);
        TournamentState state = tournament.getState();
        state.start(tournament, tournamentService);
        return this;
    }
    
    Object onActionFromFinishTournament(Long id) throws InstanceNotFoundException{
        tournament = tournamentService.findIndividualTournament(tournamentId);
        TournamentState state = tournament.getState();
        state.finish(tournament, tournamentService);
        return this;
    }
    
    Object onActionFromDeleteTournament(Long id) throws InstanceNotFoundException{
        tournamentService.deleteTournament(id);
        return Tournaments.class;
    }
    public String getStartDate(){
        return DateManagerUtil.printCalendarDate(tournament.getStartDate());
    }

 
    public String getEndDate(){
        return DateManagerUtil.printCalendarDate(tournament.getEndDate());
    }

    public String getStartEnrolmentDate(){
        return DateManagerUtil.printCalendarDate(tournament.getStartEnrolmentDate());
    }

 
    public String getEndEnrolmentDate(){
        return DateManagerUtil.printCalendarDate(tournament.getEndEnrolmentDate());
    }
    
    public String getTypeLabel() {
    	return ((tournament instanceof IndividualTournament) ? messages.get("label.individual") : messages.get("label.team"));
    }
}

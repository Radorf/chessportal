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
    
    void onActivate(Long tournamentId){
        this.tournamentId=tournamentId;
    }

    Long onPassivate() {
        return tournamentId;
    }
    
    void setupRender() {
        try {
            tournament = tournamentService.findIndividualTournament(tournamentId);
        } catch (InstanceNotFoundException ex) {
            //TODO error page
            Logger.getLogger(TournamentDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    Object onActionFromStartTournament(Long id) throws InstanceNotFoundException{
        tournamentService.generateRRIndividualTournamentRounds(id);
        return this;
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

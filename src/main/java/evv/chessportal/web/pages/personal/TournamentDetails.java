package evv.chessportal.web.pages.personal;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.model.tournamentservice.TournamentState;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import evv.chessportal.web.services.AuthenticationPolicy;
import evv.chessportal.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.PLAYER_USERS)
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
    private boolean canShowGames;
    
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
            canShowGames = state.canShowGames(tournament, tournamentService);
        } catch (InstanceNotFoundException ex) {
            //TODO error page
            Logger.getLogger(TournamentDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

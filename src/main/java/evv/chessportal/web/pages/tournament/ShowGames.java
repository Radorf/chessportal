package evv.chessportal.web.pages.tournament;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import evv.chessportal.model.game.Game;
import evv.chessportal.model.individualround.IndividualRound;
import evv.chessportal.model.individualtournament.IndividualTournament;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;

public class ShowGames {

    @Property
    private Long tournamentId;
    
    @Property
    private List<IndividualRound> roundList;
    
    @Property
    private IndividualRound individualRound;

    @Property
    private Game game;
    
    @Inject
    private TournamentService tournamentService;
    
    @Property
    private IndividualTournament tournament;
    
    void onActivate(Long tournamentId){
        this.tournamentId=tournamentId;
    }

    Long onPassivate() {
        return tournamentId;
    }
    
    void setupRender() {
        try {
            tournament = tournamentService.findIndividualTournament(tournamentId);
            roundList = tournament.getRoundList();
        } catch (InstanceNotFoundException ex) {
            //TODO error page
            Logger.getLogger(TournamentDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}

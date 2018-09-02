package evv.chessportal.web.components;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import evv.chessportal.model.custom.CustomPlayerTournamentStats;
import evv.chessportal.model.individualtournament.IndividualTournament;
import evv.chessportal.model.player.Player;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import evv.chessportal.web.pages.tournament.TournamentDetails;

public class Classification {
    @Property
    @Parameter(required=true)
    private Long tournamentId;
    @Property
    @Parameter(required=false)
    private boolean extended;
    @Inject
    private TournamentService tournamentService;
    
    @Property
    private List<CustomPlayerTournamentStats> classification;
    
    @Property
    private CustomPlayerTournamentStats stats;
    
    void setupRender() {
        try {
            classification = tournamentService.getClassification(tournamentId);
        } catch (InstanceNotFoundException ex) {
            //TODO error page
            Logger.getLogger(TournamentDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

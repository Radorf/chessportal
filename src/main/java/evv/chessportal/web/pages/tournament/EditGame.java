package evv.chessportal.web.pages.tournament;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import evv.chessportal.model.game.Game;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.model.util.exceptions.DatesInconsistenceException;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import evv.chessportal.web.services.AuthenticationPolicy;
import evv.chessportal.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class EditGame {
    @Property
    private Long gameId;
    @Inject
    private TournamentService tournamentService;
    @Property
    private Game game;
    @Property
    @Persist(PersistenceConstants.FLASH)
    private String radioSelectedValue;
    @Property
    private String whiteOption;
    @Property
    private String blackOption;
    @Property
    private String drawOption;

    @Inject
    private PageRenderLinkSource pageRenderLinkSource;
    
    void onActivate(Long gameId){
        this.gameId=gameId;
    }

    Long onPassivate() {
        return gameId;
    }
    
    void setupRender() {
        whiteOption = Game.ScoreResult.WHITE.toString();
        blackOption = Game.ScoreResult.BLACK.toString();
        drawOption = Game.ScoreResult.DRAW.toString();
        try {
            game = tournamentService.findGame(gameId);
        } catch (InstanceNotFoundException ex) {
            //TODO error page
            Logger.getLogger(TournamentDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    Object onSuccess() throws DatesInconsistenceException {
        try {
            game = tournamentService.findGame(gameId);

            if (radioSelectedValue!=null) {
                game.setScore(Game.ScoreResult.valueOf(radioSelectedValue));
                tournamentService.updateGame(game);
            }
            
            return pageRenderLinkSource.createPageRenderLinkWithContext(ShowGames.class, game.getIndividualRound().getTournament().getId());
        } catch (InstanceNotFoundException ex) {
            Logger.getLogger(EditTournament.class.getName()).log(Level.SEVERE, null, ex);
            return this;
        }
    }
}

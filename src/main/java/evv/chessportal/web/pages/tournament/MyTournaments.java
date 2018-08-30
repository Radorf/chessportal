package evv.chessportal.web.pages.tournament;

import java.util.List;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.web.services.AuthenticationPolicy;
import evv.chessportal.web.services.AuthenticationPolicyType;
import evv.chessportal.web.util.DateManagerUtil;
import evv.chessportal.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.PLAYER_USERS)
public class MyTournaments {

    @Inject
    TournamentService tournamentService;
    
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String searchKey;
    
    @Property    
    private List<Tournament> tournamentList;
    
    @Property
    private Tournament tournament;
    
    @SessionState(create = false)
    private UserSession userSession;
    
    void setupRender() {
        tournamentList = tournamentService.findTournamentsOfPlayer(userSession.getUserProfileId(), searchKey);
    }
    
    public String getStartDate(){
        return DateManagerUtil.printCalendarDate(tournament.getStartDate());
    }

 
    public String getEndDate(){
        return DateManagerUtil.printCalendarDate(tournament.getEndDate());
    }
}
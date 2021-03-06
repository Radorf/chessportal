package evv.chessportal.web.pages.open;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import evv.chessportal.web.pages.user.Users;
import evv.chessportal.web.services.AuthenticationPolicy;
import evv.chessportal.web.services.AuthenticationPolicyType;
import evv.chessportal.web.util.DateManagerUtil;
import evv.chessportal.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.PLAYER_USERS)
public class Tournaments {
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
        tournamentList = tournamentService.findOpenTournamentsOfPlayer(userSession.getUserProfileId(), searchKey);
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
    
    Object onActionFromEnrol(Long tournamentId){           
        try {
            tournamentService.enrolPlayers(tournamentId, Arrays.asList(userSession.getUserProfileId()));
        } catch (InstanceNotFoundException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
}

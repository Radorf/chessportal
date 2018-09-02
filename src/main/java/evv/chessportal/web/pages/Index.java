package evv.chessportal.web.pages;

import java.util.ArrayList;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.web.util.DateManagerUtil;
import evv.chessportal.web.util.UserSession;
import evv.chessportal.web.util.UserSession.Role;

public class Index {
	
	@Inject
    TournamentService tournamentService;
    
    @Property    
    private ArrayList<Tournament> tournamentList;
    
    @Property
    private Tournament tournament;
    
    @Inject
    private Messages messages;
    
    @Property
    @SessionState(create=false)
    private UserSession userSession;
	
	void setupRender() {
        tournamentList = tournamentService.searchHomeTournaments();
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
    
    public boolean showTable() {
    	return userSession==null || Role.PLAYER.equals(userSession.getRole());
    }
}

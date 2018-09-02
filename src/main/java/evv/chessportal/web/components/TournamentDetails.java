package evv.chessportal.web.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import evv.chessportal.model.individualtournament.IndividualTournament;
import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.web.util.DateManagerUtil;

public class TournamentDetails {

    @Parameter(required=true)
    @Property
    private Tournament tournament;
    
    @Inject
    private Messages messages;
    
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.web.pages.tournament;

import java.util.ArrayList;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.web.services.AuthenticationPolicy;
import evv.chessportal.web.services.AuthenticationPolicyType;
import evv.chessportal.web.util.DateManagerUtil;

/**
 *
 * @author E_Villodas
 */
@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class Tournaments {
    @Inject
    TournamentService tournamentService;
    
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String searchKey;
    
    @Property    
    private ArrayList<Tournament> tournamentList;
    
    @Property
    private Tournament tournament;
    
    void setupRender() {
        tournamentList = tournamentService.searchTournamentByKeyword(searchKey);
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
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.web.pages.tournament;

import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.web.util.DateManagerUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author E_Villodas
 */
public class Tournaments {
    @Inject
    TournamentService tournamentService;
    
    @Property
    private String searchKey;
    
    @Property    
    @Persist(PersistenceConstants.FLASH)
    private ArrayList<Tournament> tournamentList;
    
    @Property
    private Tournament tournament;
    
    void onPrepareForRender() {
        if (tournamentList==null || tournamentList.isEmpty()){
            tournamentList = tournamentService.searchAll();
        }
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

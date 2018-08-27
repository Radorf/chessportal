package evv.chessportal.web.pages.tournament;

import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.model.userprofile.UserProfile;
import evv.chessportal.model.userservice.UserService;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import evv.chessportal.web.pages.user.SearchUsers;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author E_Villodas
 */
public class SelectPlayers {
    @Inject
    private UserService userService;

    @Property
    private String searchKey;

    @Property    
    @Persist(PersistenceConstants.FLASH)
    private ArrayList<UserProfile> userList;

    @Property
    UserProfile user;
    
    @Property
    private Tournament tournament;
    
    @Inject
    private TournamentService tournamentService;
    
    void onActivate(Long tournamentId){
        try {
            tournament = tournamentService.findTournament(tournamentId);
        } catch (InstanceNotFoundException ex) {
            //TODO error page
            Logger.getLogger(TournamentDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     void onPrepareForRender() {
        if (userList==null || userList.isEmpty()){
            userList = userService.searchAll();
        }
    }
     
    Object onSuccess() {
//        userList =  new ArrayList<UserProfile>();
        userList = userService.searchByGeneralKey(searchKey);
        return this;
    }
    
    
}

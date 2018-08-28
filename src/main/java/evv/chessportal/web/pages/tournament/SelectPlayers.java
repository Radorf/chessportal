package evv.chessportal.web.pages.tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.model.userprofile.UserProfile;
import evv.chessportal.model.userservice.UserService;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import evv.chessportal.web.pages.Index;

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

    @Property
    @Persist
    private List<Long> idList;

    @Property
    private Long tournamentId;

    @Property
    private ValueEncoder<UserProfile> encoder = new ValueEncoder<UserProfile>() {
        @Override
        public UserProfile toValue(String clientValue) {
            UserProfile up = new UserProfile();
            up.setId(Long.parseLong(clientValue));
            return up;
        }

        @Override
        public String toClient(UserProfile value) {
            return String.valueOf(value.getId());
        }
    };

    @Inject
    private TournamentService tournamentService;

    void onActivate(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    Long onPassivate() {
        return tournamentId;
    }

    void setupRender() {
        if (idList == null) {
            idList = new ArrayList<Long>();
        }
        try {
            tournament = tournamentService.findTournament(tournamentId);
        } catch (InstanceNotFoundException ex) {
            // TODO error page
            Logger.getLogger(TournamentDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (userList == null || userList.isEmpty()) {
            userList = userService.searchAll();
        }
    }

    public boolean isSelected() {
        return (idList.contains(user.getId()));
    }

    public void setSelected(boolean selected) {
        if (selected) {
            idList.add(user.getId());
        }
    }

    void onPrepareForSubmit() {
        if (idList == null) {
            idList = new ArrayList<Long>();
        }
        try {
            tournament = tournamentService.findTournament(tournamentId);
        } catch (InstanceNotFoundException ex) {
            // TODO error page
            Logger.getLogger(TournamentDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        userList = userService.searchAll();
    }

    Object onSuccess() {
//        userList =  new ArrayList<UserProfile>();
        if (idList.isEmpty()) {
            return Index.class;
        }
        return this;
    }

}

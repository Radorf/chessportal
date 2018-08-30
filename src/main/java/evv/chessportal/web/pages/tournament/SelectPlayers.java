package evv.chessportal.web.pages.tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import evv.chessportal.model.individualtournament.IndividualTournament;
import evv.chessportal.model.player.Player;
import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.model.userprofile.UserProfile;
import evv.chessportal.model.userservice.UserService;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import evv.chessportal.web.pages.Index;
import evv.chessportal.web.services.AuthenticationPolicy;
import evv.chessportal.web.services.AuthenticationPolicyType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author E_Villodas
 */
@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class SelectPlayers {

    @Inject
    private UserService userService;

    @Persist(PersistenceConstants.FLASH)
    @Property
    private String searchKey;

    @Property
    private ArrayList<Player> userList;

    @Property
    UserProfile user;

    @Property
    private Tournament tournament;

    @Property
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
            if (tournament instanceof IndividualTournament) {
                Set<Player> playerList = ((IndividualTournament) tournament).getPlayerList();
                // Lambda to get playerIds
                idList = playerList.stream().map(Player::getId).collect(Collectors.toList());
            }
        } catch (InstanceNotFoundException ex) {
            // TODO error page
            Logger.getLogger(TournamentDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (userList == null || userList.isEmpty()) {
            userList = tournamentService.searchPlayerByKeyword(searchKey);
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
        userList = tournamentService.searchPlayerByKeyword("");
    }

    Object onSuccessFromSelectForm() {
//        userList =  new ArrayList<UserProfile>();
        if (idList.isEmpty()) {
            return Index.class;
        }
        try {
            tournamentService.enrolPlayers(tournamentId, idList);
        } catch (InstanceNotFoundException ex) {
            // TODO error page
            Logger.getLogger(TournamentDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
}

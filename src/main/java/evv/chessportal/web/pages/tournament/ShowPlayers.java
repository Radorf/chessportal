/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.web.pages.tournament;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import evv.chessportal.model.player.Player;
import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.model.tournamentservice.TournamentState;
import evv.chessportal.model.userprofile.UserProfile;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import evv.chessportal.web.services.AuthenticationPolicy;
import evv.chessportal.web.services.AuthenticationPolicyType;

/**
 *
 * @author E_Villodas
 */
@AuthenticationPolicy(AuthenticationPolicyType.ADMIN_USERS)
public class ShowPlayers {

	@Property
	private Tournament tournament;

	private Long tournamentId;

	@Persist(PersistenceConstants.FLASH)
	@Property
	private String searchKey;

	@Property
	private ArrayList<Player> userList;

	@Property
	private UserProfile user;

	@Inject
	private TournamentService tournamentService;

	@Inject
	private Messages messages;

	@Property
	private boolean canEnrolPlayers;

	void onActivate(Long tournamentId) {
		this.tournamentId = tournamentId;
	}

	Long onPassivate() {
		return tournamentId;
	}

	void setupRender() {
        try {
            tournament = tournamentService.findIndividualTournament(tournamentId);
        } catch (InstanceNotFoundException ex) {
            // TODO error page
            Logger.getLogger(TournamentDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
		userList = tournamentService.searchPlayerByTournamentAndKeyword(tournamentId, searchKey);
		TournamentState state = tournament.getState();
		canEnrolPlayers = state.canShowPlayers(tournament, tournamentService);
	}

	public Long getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}

}

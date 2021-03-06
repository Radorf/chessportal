/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.tournamentservice;

import evv.chessportal.model.custom.CustomPlayerTournamentStats;
import evv.chessportal.model.game.Game;
import evv.chessportal.model.individualround.IndividualRound;
import evv.chessportal.model.individualtournament.IndividualTournament;
import evv.chessportal.model.player.Player;
import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.util.exceptions.DatesInconsistenceException;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author E_Villodas
 */
public interface TournamentService {

    IndividualTournament createRRIndividualTournament(String name_, Calendar startDate, Calendar endDate, Calendar startEnrolmentDate, Calendar endEnrolmentDate) throws DatesInconsistenceException;

    Tournament updateTournament(Long tournamentId, String name_, Calendar startDate, Calendar endDate, Calendar startEnrolmentDate,
            Calendar endEnrolmentDate) throws DatesInconsistenceException, InstanceNotFoundException;
    
    void deleteTournament(Long id)throws InstanceNotFoundException;
    
    ArrayList<Tournament> searchTournamentByKeyword(String keyword);
    
    ArrayList<Tournament> searchHomeTournaments();
    
    ArrayList<Tournament> searchAll();

    IndividualTournament findIndividualTournament(Long tournamentId) throws InstanceNotFoundException;
    
    void enrolPlayers(Long tournamentId, final Collection<Long> playerIds) throws InstanceNotFoundException;
    
    ArrayList<Player> searchPlayerByTournamentAndKeyword(Long tournamentId, String keyword);
    
    ArrayList<Player> searchPlayerByKeyword(String keyword);

    IndividualTournament generateRRIndividualTournamentRounds(Long id) throws InstanceNotFoundException;
    
    List<Tournament> findTournamentsOfPlayer(Long playerId, String searchKey);
    
    List<Tournament> findOpenTournamentsOfPlayer(Long playerId, String searchKey);

    Game findGame(Long gameId) throws InstanceNotFoundException;

    Game updateGame(Game game);

    List<CustomPlayerTournamentStats> getClassification(Long tournamentId) throws InstanceNotFoundException;

    boolean hasAllGamesScored(Long tournamentId);

    void changeTournamentStatus(Tournament context, TournamentState state) throws InstanceNotFoundException;
}

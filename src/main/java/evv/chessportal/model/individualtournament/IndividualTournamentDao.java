/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.individualtournament;

import java.util.List;

import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.util.dao.GenericDao;

/**
 *
 * @author E_Villodas
 */
public interface IndividualTournamentDao extends GenericDao<IndividualTournament, Long> {

    List<Tournament> findTournamentsOfPlayer(Long playerId, String searchKey);

    List<Tournament> findOpenTournamentsOfPlayer(Long playerId, String searchKey);
    
}

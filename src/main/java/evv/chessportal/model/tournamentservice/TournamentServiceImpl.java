/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.tournamentservice;

import evv.chessportal.model.individualtournament.IndividualTournament;
import evv.chessportal.model.individualtournament.IndividualTournamentDao;
import evv.chessportal.model.player.Player;
import evv.chessportal.model.player.PlayerDao;
import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.tournament.TournamentDao;
import evv.chessportal.model.util.exceptions.DatesInconsistenceException;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author E_Villodas
 */
@Transactional
public class TournamentServiceImpl implements TournamentService {
    
    @Autowired
    private IndividualTournamentDao individualTournamentDao;
    @Autowired
    private TournamentDao tournamentDao;
    @Autowired
    private PlayerDao playerDao;

    public void setIndividualTournamentDao(IndividualTournamentDao individualTournamentDao) {
        this.individualTournamentDao = individualTournamentDao;
    }

    public void setTournamentDao(TournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
    }

    public void setPlayerDao(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public IndividualTournament createRRIndividualTournament(String name_, Calendar startDate, Calendar endDate, Calendar startEnrolmentDate, Calendar endEnrolmentDate) throws DatesInconsistenceException{
        IndividualTournament tournament;
        
        if (name_ == null) return null;
        if (startDate == null) return null;          
        tournament = new IndividualTournament();
         if  (startDate.after(endDate) )  {
             throw new DatesInconsistenceException(startDate + " later than "+ endDate,Tournament.class.getName()) ;
         }
         if (startEnrolmentDate!=null && startEnrolmentDate.after(endEnrolmentDate)){
             throw new DatesInconsistenceException(startEnrolmentDate + " later than "+ endEnrolmentDate,Tournament.class.getName()) ;
         }
        tournament.setName_(name_);
        tournament.setStartDate(startDate);
        tournament.setEndDate(endDate);
        tournament.setStartEnrolmentDate(startEnrolmentDate);
        tournament.setEndEnrolmentDate(endEnrolmentDate);
        tournament.setPairingsType(Tournament.TournamentPairingsType.ROUND_ROBIN);
        individualTournamentDao.save(tournament);
        return tournament;
        
    }
    
    @Transactional
    @Override
    public Tournament updateTournament(Long tournamentId, String name_, Calendar startDate, Calendar endDate, Calendar startEnrolmentDate,
            Calendar endEnrolmentDate) throws DatesInconsistenceException, InstanceNotFoundException {
       if (name_ == null) return null;
        if (startDate == null) return null; 
        Tournament tournament = individualTournamentDao.find(tournamentId);
         if  (startDate.after(endDate) )  {
             throw new DatesInconsistenceException(startDate + " later than "+ endDate,Tournament.class.getName()) ;
         }
         if (startEnrolmentDate!=null && startEnrolmentDate.after(endEnrolmentDate)){
             throw new DatesInconsistenceException(startEnrolmentDate + " later than "+ endEnrolmentDate,Tournament.class.getName()) ;
         }
        tournament.setName_(name_);
        tournament.setStartDate(startDate);
        tournament.setEndDate(endDate);
        tournament.setStartEnrolmentDate(startEnrolmentDate);
        tournament.setEndEnrolmentDate(endEnrolmentDate);
        tournamentDao.save(tournament);
        return tournament;
    }

    @Transactional(readOnly = true)
    @Override
    public ArrayList<Tournament> searchTournamentByKeyword(String keyword) {
        return tournamentDao.searchTournamentByKeyword(keyword);
    }
    
    @Transactional(readOnly = true)
    @Override
    public ArrayList<Tournament> searchAll() {
        return tournamentDao.searchAll();
    }

    @Transactional(readOnly = true)
    @Override    
    public Tournament findTournament(Long tournamentId) throws InstanceNotFoundException {
        return tournamentDao.find(tournamentId);
    }

    @Override
    public void enrolPlayers(Long tournamentId, final Collection<Long> playerIds) throws InstanceNotFoundException {
        IndividualTournament tournament = individualTournamentDao.find(tournamentId);
        Set<Player> playerList = new HashSet<Player>();
        for (Long playerId : playerIds) {
            playerList.add(playerDao.find(playerId));
        }
        if (tournament.getPlayerList()==null) {
            tournament.setPlayerList(playerList);
        } else {
            tournament.getPlayerList().addAll(playerList);
        }
        individualTournamentDao.save(tournament);
    }
    
    public ArrayList<Player> searchPlayerByKeyword(String keyword) {
        return playerDao.searchByKeyword(keyword);
    }

    @Override
    public void generateRRIndividualTournamentRounds(Long id) throws InstanceNotFoundException {
        IndividualTournament individualTournament = individualTournamentDao.find(id);
        //individualTournamentDao.countEnrolledPlayers(individualTournament);
    }
    
    @Override
    public List<Tournament> findTournamentsOfPlayer(Long playerId, String searchKey) {
        return individualTournamentDao.findTournamentsOfPlayer(playerId, searchKey);
    }

    @Override
    public List<Tournament> findOpenTournamentsOfPlayer(Long playerId, String searchKey) {
        return individualTournamentDao.findOpenTournamentsOfPlayer(playerId, searchKey);
    }
}

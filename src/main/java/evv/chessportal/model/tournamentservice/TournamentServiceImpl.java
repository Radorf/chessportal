/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.tournamentservice;

import evv.chessportal.model.custom.CustomPlayerTournamentStats;
import evv.chessportal.model.game.Game;
import evv.chessportal.model.game.GameDao;
import evv.chessportal.model.individualround.IndividualRound;
import evv.chessportal.model.individualround.IndividualRoundDao;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author E_Villodas
 */
public class TournamentServiceImpl implements TournamentService {
    
    @Autowired
    private IndividualTournamentDao individualTournamentDao;
    @Autowired
    private TournamentDao tournamentDao;
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private IndividualRoundDao individualRoundDao;
    @Autowired
    private GameDao gameDao;

    public void setIndividualTournamentDao(IndividualTournamentDao individualTournamentDao) {
        this.individualTournamentDao = individualTournamentDao;
    }

    public void setTournamentDao(TournamentDao tournamentDao) {
        this.tournamentDao = tournamentDao;
    }

    public void setPlayerDao(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public void setIndividualRoundDao(IndividualRoundDao individualRoundDao) {
        this.individualRoundDao = individualRoundDao;
    }

    public void setGameDao(GameDao gameDao) {
        this.gameDao = gameDao;
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

    @Transactional
    @Override
    public void deleteTournament(Long id) throws InstanceNotFoundException{
        IndividualTournament tournament = individualTournamentDao.find(id);
        tournament.setDeleted(true);
        individualTournamentDao.save(tournament);
    }

    @Override
    public ArrayList<Tournament> searchTournamentByKeyword(String keyword) {
        return tournamentDao.searchTournamentByKeyword(keyword);
    }
    
    @Override
    public ArrayList<Tournament> searchHomeTournaments() {
        return tournamentDao.searchHomeTournaments();
    }
    
    @Override
    public ArrayList<Tournament> searchAll() {
        return tournamentDao.searchAllOrderByStartDate();
    }

    @Override
    public IndividualTournament findIndividualTournament(Long tournamentId) throws InstanceNotFoundException {
        return individualTournamentDao.find(tournamentId);
    }

    @Transactional
    @Override
    public void enrolPlayers(Long tournamentId, final Collection<Long> playerIds) throws InstanceNotFoundException {
        IndividualTournament tournament = individualTournamentDao.find(tournamentId);
        Set<Player> playerList = new HashSet<>();
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
    
    @Override
    public ArrayList<Player> searchPlayerByTournamentAndKeyword(Long tournamentId, String keyword) {
        return playerDao.searchByTournamentAndKeyword(tournamentId, keyword);
    }
    
    @Override
    public ArrayList<Player> searchPlayerByKeyword(String keyword) {
        return playerDao.searchByKeyword(keyword);
    }

    @Transactional
    @Override
    public IndividualTournament generateRRIndividualTournamentRounds(Long id) throws InstanceNotFoundException {
        IndividualTournament individualTournament = individualTournamentDao.find(id);
        ArrayList<Player> players = new ArrayList<> (individualTournament.getPlayerList());
        int nPlayers = players.size();        
        if(nPlayers % 2 == 1) { //We force having and even number of teams
            players.add(new Player ("-"));
            nPlayers++;
        }
        int nRounds= nPlayers - 1; 
        int nGamesR = nPlayers/2;
        ArrayList <IndividualRound> rounds = initRounds(nRounds, individualTournament,nGamesR);  
        
        //In the first round players play against the 'next'
        List<Game> games = rounds.get(0).getGameList();
         for(Game g: games){      // set Round 1 games
            int ngame = games.indexOf(g);
            g.setWhitePiecesPlayer(players.get(ngame*2));
            g.setBlackPiecesPlayer(players.get(ngame*2 + 1));
        }
         Player pivot = rounds.get(0).getGameList().get(0).getBlackPiecesPlayer();        
         boolean isEvenRound = true;
         for(int i = 1; i<nRounds; i++ ){
             if (isEvenRound) {
                 pairingsForEvenRound(rounds.get(i),rounds.get(i-1),pivot);
             } else {
                 pairingsForOddRound(rounds.get(i),rounds.get(i-1),pivot);
             }
          isEvenRound=!isEvenRound;      
         }
         return individualTournament;
         
    }    
    
    private void pairingsForEvenRound(IndividualRound thisRound, IndividualRound beforeRound, Player pivot) {
        List<Game> games = thisRound.getGameList();
        List<Game>beforeGames = beforeRound.getGameList();
        // In even rounds we move the pivot player to white in the last game and then we zig zag upwards
                int n = games.size();            
        for (int i=0;i<n-1;i++){            
           games.get(i).setBlackPiecesPlayer(beforeGames.get(i).getWhitePiecesPlayer());
           games.get(i).setWhitePiecesPlayer(beforeGames.get(i+1).getBlackPiecesPlayer());
        }
        games.get(n-1).setBlackPiecesPlayer(beforeGames.get(n-1).getWhitePiecesPlayer());
        games.get(n-1).setWhitePiecesPlayer(pivot);
        
    }
    private void pairingsForOddRound(IndividualRound thisRound, IndividualRound beforeRound, Player pivot){
        List<Game> games = thisRound.getGameList();
        List<Game>beforeGames = beforeRound.getGameList();
        int n = games.size();            
        //Indexes are tricky!
        for (int i=n-1;i>0;i--){ 
           games.get(i).setBlackPiecesPlayer(beforeGames.get(i).getWhitePiecesPlayer());
           games.get(i).setWhitePiecesPlayer(beforeGames.get(i-1).getBlackPiecesPlayer());
        }   
        //In odd rounds the pivot get to its starting position
        games.get(0).setBlackPiecesPlayer(pivot);     
        //The last game black player and the first game white player remain fixed and then we zig zag downwards.
        games.get(n-1).setBlackPiecesPlayer(beforeGames.get(n-1).getBlackPiecesPlayer());
        games.get(0).setWhitePiecesPlayer(beforeGames.get(0).getWhitePiecesPlayer());
         //games.get(n-1).getBlackPiecesPlayer() has been hammered
        games.get(n-1).setWhitePiecesPlayer(beforeGames.get(n-2).getBlackPiecesPlayer());
    }

    

    private ArrayList<IndividualRound> initRounds(int nRounds, IndividualTournament individualTournament,int nGamesR) {
        ArrayList <IndividualRound> rounds = new ArrayList<>(nRounds);
        for (int i=0;i<nRounds;i++){
            IndividualRound r = new IndividualRound(individualTournament);
            individualRoundDao.save(r);
            rounds.add(r);
            ArrayList<Game> games = initGames(nGamesR);
            r.setGameList(games);
            r.setNumber_(i+1);
        }
        individualTournament.setRoundList(rounds);
        return rounds;
    }
    private ArrayList<Game> initGames(int nGamesR) {
        ArrayList<Game> games = new ArrayList<>(nGamesR);
        for(int i=0;i<nGamesR;i++){
            Game g = new Game();
            gameDao.save(g);
            games.add(g);
        }
        return games;
    }
    
    @Override
    public List<Tournament> findTournamentsOfPlayer(Long playerId, String searchKey) {
        return individualTournamentDao.findTournamentsOfPlayer(playerId, searchKey);
    }

    @Override
    public List<Tournament> findOpenTournamentsOfPlayer(Long playerId, String searchKey) {
        return individualTournamentDao.findOpenTournamentsOfPlayer(playerId, searchKey);
    }

    @Override
    public Game findGame(Long gameId) throws InstanceNotFoundException {
        return gameDao.find(gameId);
    }  

    @Transactional
    @Override
    public Game updateGame(Game game) {
        gameDao.save(game);
        return game;
    }

    @Override
    public List<CustomPlayerTournamentStats> getClassification(Long tournamentId) throws InstanceNotFoundException {
        IndividualTournament tournament = findIndividualTournament(tournamentId);
        Map<Long, CustomPlayerTournamentStats> playerStats = new HashMap<>();
        for (Player player : tournament.getPlayerList()) {
            playerStats.put(player.getId(), new CustomPlayerTournamentStats(player));
        }
        for (IndividualRound round : tournament.getRoundList()) {
            for (Game game : round.getGameList()){ 
                if (game.getScore()!=null) {
                    Player whitePiecesPlayer = game.getWhitePiecesPlayer();
                    Player blackPiecesPlayer = game.getBlackPiecesPlayer();
                    switch (game.getScore()) {
                    case WHITE:
                        playerStats.get(whitePiecesPlayer.getId()).addWin(true);
                        playerStats.get(blackPiecesPlayer.getId()).addLost(false);
                        break;
                    case BLACK:
                        playerStats.get(whitePiecesPlayer.getId()).addLost(true);
                        playerStats.get(blackPiecesPlayer.getId()).addWin(false);
                        break;
                    case DRAW:
                        playerStats.get(whitePiecesPlayer.getId()).addDraw(true);
                        playerStats.get(blackPiecesPlayer.getId()).addDraw(false);
                        break;
                    }
                }
            }
        }
        List<CustomPlayerTournamentStats> values = new ArrayList<>(playerStats.values());
        Collections.sort(values, new Comparator<CustomPlayerTournamentStats>() {
            @Override
            public int compare(CustomPlayerTournamentStats o1, CustomPlayerTournamentStats o2) {
                return Math.round((o2.getScore()-o1.getScore())*2);
            }
        });
        return values;
    }

    @Override
    public boolean hasAllGamesScored(Long tournamentId) {
        return individualTournamentDao.hasAllGamesScored(tournamentId);
    }

    @Transactional
    @Override
    public void changeTournamentStatus(Tournament context, TournamentState state) throws InstanceNotFoundException {
        IndividualTournament tournament = individualTournamentDao.find(context.getId());
        tournament.setState(state);
        individualTournamentDao.save(tournament);
    }      

      
}

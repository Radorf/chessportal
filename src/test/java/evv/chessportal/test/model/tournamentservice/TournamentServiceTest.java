/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.test.model.tournamentservice;

import evv.chessportal.model.game.Game;
import evv.chessportal.model.individualround.IndividualRound;
import evv.chessportal.model.individualtournament.IndividualTournament;
import evv.chessportal.model.tournamentservice.TournamentService;
import evv.chessportal.model.userservice.PersonDetails;
import evv.chessportal.model.userservice.UserService;
import static evv.chessportal.model.util.GlobalNames.SPRING_CONFIG_FILE;
import evv.chessportal.model.util.exceptions.DatesInconsistenceException;
import evv.chessportal.model.util.exceptions.DuplicateInstanceException;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import static evv.chessportal.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author E_Villodas
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE})
@Transactional
public class TournamentServiceTest {
    
    @Autowired
    private UserService userService;
     @Autowired
    private TournamentService tournamentService;
         @Test
    public void generateRRIndividualTournamentRoundstest() throws DatesInconsistenceException, DuplicateInstanceException, InstanceNotFoundException{
        int n = 8;
        IndividualTournament individualTournament = tournamentService.createRRIndividualTournament("TestTournament", Calendar.getInstance(),
                null, null, null);
        ArrayList<Long> playerIds=new ArrayList<>(n);
        for(int i = 0;i<n;i++){
             playerIds.add(i,userService.createUser("jugador"+ (i+1), "admin", null, null, new PersonDetails(null, null, null, null), false).getId());
        }
        tournamentService.enrolPlayers(individualTournament.getId(), playerIds);
        individualTournament=tournamentService.generateRRIndividualTournamentRounds(individualTournament.getId());
        
        List<IndividualRound> rounds = individualTournament.getRoundList() ;
        for(IndividualRound r: rounds){
            System.out.println("Round " + r.getNumber_());
            List<Game> games = r.getGameList();
            for (Game g: games){
                System.out.println(g.getWhitePiecesPlayer().getLoginName()+" - " + g.getBlackPiecesPlayer().getLoginName());
            }
        }
        assertEquals(100,100);
    }
    
}

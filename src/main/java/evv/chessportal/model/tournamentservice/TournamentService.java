/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.tournamentservice;

import evv.chessportal.model.individualtournament.IndividualTournament;
import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.util.exceptions.DatesInconsistenceException;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author E_Villodas
 */
public interface TournamentService {
    
    public IndividualTournament createRRIndividualTournament(String name_, Calendar startDate, Calendar endDate, Calendar startEnrolmentDate,
            Calendar endEnrolmentDate, Tournament.TournamentPairingsType type) throws DatesInconsistenceException;

    public ArrayList<Tournament> searchAll();

    public Tournament findTournament(Long tournamentId) throws InstanceNotFoundException;;
}

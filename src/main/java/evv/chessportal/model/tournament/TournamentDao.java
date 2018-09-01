/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.tournament;

import evv.chessportal.model.util.dao.GenericDao;
import java.util.ArrayList;

/**
 *
 * @author E_Villodas
 */
public interface TournamentDao extends GenericDao<Tournament, Long> {

    public ArrayList<Tournament> searchAll();

    ArrayList<Tournament> searchTournamentByKeyword(String keyword);

}

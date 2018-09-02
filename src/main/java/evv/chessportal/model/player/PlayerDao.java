/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.player;

import java.util.ArrayList;

import evv.chessportal.model.util.dao.GenericDao;

/**
 *
 * @author E_Villodas
 */
public interface PlayerDao extends GenericDao<Player,Long>{
    ArrayList<Player> searchByKeyword(String keyword);
    ArrayList<Player> searchByTournamentAndKeyword(Long tournamentId, String keyword);
}

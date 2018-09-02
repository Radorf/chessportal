/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.tournament;

import evv.chessportal.model.individualtournament.IndividualTournament;
import evv.chessportal.model.player.Player;
import evv.chessportal.model.util.dao.GenericDaoHibernate;
import java.util.ArrayList;

import org.hibernate.query.Query;

/**
 *
 * @author E_Villodas
 */
public class TournamentDaoHibernate extends GenericDaoHibernate<Tournament,Long> implements TournamentDao{

    @Override
    public ArrayList<Tournament> searchAllOrderByStartDate() {
        ArrayList <Tournament> list;
        list = (ArrayList <Tournament>) getSession().createQuery(
                "SELECT t FROM Tournament t "
                + "ORDER BY t.startDate DESC"
        ).list();
        
        return list;
    }

    @Override
    public ArrayList<Tournament> searchTournamentByKeyword(String searchKey) {
        boolean hasKeyword = searchKey!=null && !"".equals(searchKey);
        StringBuilder sb = new StringBuilder(64);
        sb.append("SELECT t FROM Tournament t ");
        if (hasKeyword) {
            sb.append("WHERE t.name_ LIKE :searchKey ");
        }
        sb.append("ORDER BY t.startDate DESC ");
        
        Query query = getSession().createQuery(sb.toString());
        if (hasKeyword) {
            query.setParameter("searchKey", "%"+searchKey+"%");
        }
        ArrayList<Tournament> list = (ArrayList<Tournament>) query.list();

        return list;
    }

    
}

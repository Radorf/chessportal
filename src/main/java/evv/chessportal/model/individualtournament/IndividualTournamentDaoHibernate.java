/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.individualtournament;

import evv.chessportal.model.player.Player;
import evv.chessportal.model.tournament.Tournament;
import evv.chessportal.model.util.dao.GenericDaoHibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.TemporalType;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author E_Villodas
 */
@Repository("individualTournamentDao")
public class IndividualTournamentDaoHibernate extends GenericDaoHibernate<IndividualTournament,Long> implements IndividualTournamentDao {

    @Override
    public List<Tournament> findTournamentsOfPlayer(Long playerId, String searchKey) {
        boolean hasKeyword = searchKey!=null && !"".equals(searchKey);
        StringBuilder sb = new StringBuilder(64);
        sb.append("SELECT t FROM IndividualTournament t ");
        sb.append("INNER JOIN t.playerList pl ");
        sb.append("WHERE pl.id =:playerId ");
        if (hasKeyword) {
            sb.append("AND t.name_ LIKE :searchKey ");
        }
        sb.append("ORDER BY t.startDate ");
        
        Query query = getSession().createQuery(sb.toString());
        query.setParameter("playerId", playerId);
        if (hasKeyword) {
            query.setParameter("searchKey", "%"+searchKey+"%");
        }
        List<Tournament> list = (List<Tournament>) query.list();

        return list;
    }

    @Override
    public List<Tournament> findOpenTournamentsOfPlayer(Long playerId, String searchKey) {
        boolean hasKeyword = searchKey!=null && !"".equals(searchKey);
        StringBuilder sb = new StringBuilder(64);
        sb.append("SELECT t FROM IndividualTournament t where t.id NOT IN ( ");
        sb.append("SELECT t.id FROM IndividualTournament t ");
        sb.append("INNER JOIN t.playerList pl ");
        sb.append("WHERE pl.id =:playerId) ");
        sb.append(" AND (t.startEnrolmentDate is null or t.startEnrolmentDate < :currentCalendar) ");
        sb.append(" AND (t.endEnrolmentDate is null or t.endEnrolmentDate > :currentCalendar) ");
        if (hasKeyword) {
            sb.append(" AND t.name_ LIKE :searchKey ");
        }
        sb.append("ORDER BY t.startDate ");
        
        Calendar currentCalendar = Calendar.getInstance();
        Query query = getSession().createQuery(sb.toString());
        query.setParameter("playerId", playerId);
        query.setParameter("currentCalendar", currentCalendar);
        if (hasKeyword) {
            query.setParameter("searchKey", "%"+searchKey+"%");
        }
        List<Tournament> list = (List<Tournament>) query.list();

        return list;
    }
    
}

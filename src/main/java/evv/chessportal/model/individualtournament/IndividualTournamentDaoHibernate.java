/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.individualtournament;

import evv.chessportal.model.util.dao.GenericDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author E_Villodas
 */
@Repository("individualTournamentDao")
public class IndividualTournamentDaoHibernate extends GenericDaoHibernate<IndividualTournament,Long> implements IndividualTournamentDao {
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.tournament;

import evv.chessportal.model.util.dao.GenericDaoHibernate;
import java.util.ArrayList;

/**
 *
 * @author E_Villodas
 */
public class TournamentDaoHibernate extends GenericDaoHibernate<Tournament,Long> implements TournamentDao{

    @Override
    public ArrayList<Tournament> searchAll() {
        ArrayList <Tournament> list;
        list = (ArrayList <Tournament>) getSession().createQuery(
                "SELECT t FROM Tournament t "
                + "ORDER BY t.startDate DESC"
        ).list();
        
        return list;
    }
    
}

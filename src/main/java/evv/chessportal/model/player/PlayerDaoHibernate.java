/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.player;

import java.util.ArrayList;

import org.hibernate.query.Query;

import evv.chessportal.model.userprofile.UserProfile;
import evv.chessportal.model.util.dao.GenericDaoHibernate;

/**
 *
 * @author E_Villodas
 */
public class PlayerDaoHibernate extends GenericDaoHibernate<Player,Long> implements PlayerDao{
    @Override
    public ArrayList<Player> searchByKeyword(String searchKey) {
        boolean hasKeyword = searchKey!=null && !"".equals(searchKey);
        StringBuilder sb = new StringBuilder(64);
        sb.append("SELECT u FROM Player u ");
        if (hasKeyword) {
            sb.append("WHERE u.loginName LIKE :searchKey ");
            sb.append("OR  u.person.firstName LIKE :searchKey ");
            sb.append("OR u.person.surname LIKE :searchKey");
        }
        
        Query query = getSession().createQuery(sb.toString());
        if (hasKeyword) {
            query.setParameter("searchKey", "%"+searchKey+"%");
        }
        ArrayList<Player> list = (ArrayList<Player>) query.list();

        return list;
    }
}

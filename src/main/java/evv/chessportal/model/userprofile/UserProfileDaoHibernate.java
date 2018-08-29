/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.userprofile;

import evv.chessportal.model.player.Player;
import evv.chessportal.model.util.dao.GenericDaoHibernate;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import java.util.ArrayList;
import javax.persistence.NoResultException;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository("userProfileDao")
public class UserProfileDaoHibernate extends
        GenericDaoHibernate<UserProfile, Long> implements UserProfileDao {

    @Override
    public UserProfile findByLoginName(String loginName) throws InstanceNotFoundException {

        UserProfile userProfile = null;
        try {
            userProfile = (UserProfile) getSession().createQuery(
                    "SELECT u FROM UserProfile u WHERE u.loginName = :loginName")
                    .setParameter("loginName", loginName)
                    .getSingleResult();
        } catch (NoResultException nre) {
            throw new InstanceNotFoundException(loginName, UserProfile.class.getName());
        }
        return userProfile;
    }

//    @Override
//    public <UserProfile> ArrayList searchByGeneralKey(String searchKey) {
//        ArrayList<UserProfile> list = null;
//        list = (ArrayList<UserProfile>) getSession().createQuery(
//                "SELECT u FROM UserProfile u "
//                + "WHERE u.loginName LIKE :searchKey "
//                + "OR  u.person.firstName LIKE :searchKey "
//                + "OR uperson.surname LIKE :searchKey")
//                .setParameter("searchKey", searchKey).list();
//        ;
//
//        return list;
    @Override
    public ArrayList<UserProfile> searchByGeneralKey(String searchKey) {
        boolean hasKeyword = searchKey!=null && !"".equals(searchKey);
        StringBuilder sb = new StringBuilder(64);
        sb.append("SELECT u FROM UserProfile u ");
        if (hasKeyword) {
            sb.append("WHERE u.loginName LIKE :searchKey ");
            sb.append("OR  u.person.firstName LIKE :searchKey ");
            sb.append("OR u.person.surname LIKE :searchKey");
        }
        
        Query query = getSession().createQuery(sb.toString());
        if (hasKeyword) {
            query.setParameter("searchKey", "%"+searchKey+"%");
        }
        ArrayList<UserProfile> list = (ArrayList<UserProfile>) query.list();

        return list;
    }
    
    @Override
    public ArrayList<UserProfile> searchAll() {
        ArrayList<UserProfile> list;
        list = (ArrayList<UserProfile>) getSession().createQuery(
                "SELECT u FROM UserProfile u ").list();

        return list;
    }

}

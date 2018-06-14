/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.userprofile;

import evv.chessportal.model.util.dao.GenericDaoHibernate;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import java.util.ArrayList;
import javax.persistence.NoResultException;

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
        ArrayList<UserProfile> list;
        list = (ArrayList<UserProfile>) getSession().createQuery(
                "SELECT u FROM UserProfile u "
                + "WHERE u.loginName LIKE :searchKey "
                + "OR  u.person.firstName LIKE :searchKey "
                + "OR u.person.surname LIKE :searchKey")
                .setParameter("searchKey", searchKey).list();

        return list;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.userprofile;

import evv.chessportal.model.util.dao.GenericDaoHibernate;
import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;



@Repository("userProfileDao")
public class UserProfileDaoHibernate extends
		GenericDaoHibernate<UserProfile, Long> implements UserProfileDao {

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

}
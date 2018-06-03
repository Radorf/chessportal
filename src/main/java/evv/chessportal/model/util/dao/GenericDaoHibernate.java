/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evv.chessportal.model.util.dao;


import evv.chessportal.model.util.exceptions.InstanceNotFoundException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author E_Villodas
 */
public class GenericDaoHibernate <E, PK extends Serializable> implements
GenericDao<E, PK> {

	private SessionFactory sessionFactory;

	private Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public GenericDaoHibernate() {
		this.entityClass = (Class<E>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(E entity) {
		getSession().saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	public E find(PK id) throws InstanceNotFoundException {
		E entity = (E) getSession().get(entityClass, id);
		if (entity == null) {
			throw new InstanceNotFoundException(id, entityClass.getName());
		}
		return entity;
	}

	public void remove(PK id) throws InstanceNotFoundException {
		getSession().delete(find(id));
	}

}
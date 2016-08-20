/**
 *
 */
package com.mytest.app.groups.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.mytest.app.groups.model.Group;

/**
 * @author yqwu
 *
 */
@Transactional
public class GroupDAOImpl implements GroupDAO {

	private SessionFactory sessionFactory;

	public GroupDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<Group> getGroups() {
		List<Group> groups = null;
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		try {
			// session.beginTransaction();
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			Query query = session
					.createQuery("from Group g order by g.groupName desc");
			groups = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return groups;
	}

	public Group getGroup(Long id) {
		Group group = null;
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		try {
			// session.beginTransaction();
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			Query query = session
					.createQuery("from Group g where g.id = " + id);
			group = (Group) query.uniqueResult();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return group;
	}

}

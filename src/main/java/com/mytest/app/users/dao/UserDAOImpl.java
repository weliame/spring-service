/**
 *
 */
package com.mytest.app.users.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.mytest.app.activities.model.Activity;
import com.mytest.app.users.model.User;

/**
 * @author yqwu
 *
 */
@Transactional
public class UserDAOImpl implements UserDAO {

	private SessionFactory sessionFactory;

	public UserDAOImpl(	SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public User getUser(Long id) {
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		User result = null;
		String userSql = "from User where id=" + id;
		try {
			// session.beginTransaction();
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			Query queryUserId = session.createQuery(userSql);
			queryUserId.setMaxResults(1);
			result = (User) queryUserId.uniqueResult();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return result;
	}

	public User getUser(String userName, Long mobileNumber) {
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		User result = null;
		String userSql = "from User where userName='" + userName
				+ "' and mobile=" + mobileNumber;
		try {
			if (userName.equals(null) || userName.equals("")) {
				userSql = "from User where mobile=" + mobileNumber;
			}
		} catch (NullPointerException e) {
			userSql = "from User where mobile=" + mobileNumber;
		}
		try {
			// session.beginTransaction();
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			Query queryUserId = session.createQuery(userSql);
			queryUserId.setMaxResults(1);
			result = (User) queryUserId.uniqueResult();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return result;
	}

	public int add(User user) {
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		int result = 0;
		user.setCreatedAt(new Date());
		try {
			// session.beginTransaction();
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			session.save(user);
			session.getTransaction().commit();
			result = 1;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return result;
	}

	public int update(User user) {
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		int result = 0;
		user.setCreatedAt(new Date());
		try {
			// session.beginTransaction();
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			session.saveOrUpdate(user);
			session.getTransaction().commit();
			result = 1;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return result;
	}

	public List<User> getJoinedUsersOfActivity(Activity activity) {
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		List<User> result = new ArrayList<User>();
		String usersSql = "from User u where u.id in "
				+ "(select userId from UsersActivitiesRef ua where ua.activityId = "
				+ activity.getId() + ") " + "order by u.userName asc";
		try {
			// session.beginTransaction();
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			Query query = session.createQuery(usersSql);
			result = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return result;
	}

	public List<User> getNotJoinedUsersOfActivity(Activity activity) {
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		List<User> result = new ArrayList<User>();
		String usersSql = "from User u where u.id in "
				+ "(select userId from UsersGroupsRef ug where ug.groupId="
				+ activity.getGroupId()
				+ ") "
				+ "and u.id not in (select userId from UsersActivitiesRef ua where ua.activityId = "
				+ activity.getId() + ")" + "order by u.userName asc";
		try {
			// session.beginTransaction();
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			if (activity.getGroupId() != null) {
				Query query = session.createQuery(usersSql);
				result = query.list();
				session.getTransaction().commit();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return result;
	}

}

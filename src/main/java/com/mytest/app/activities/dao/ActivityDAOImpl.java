/**
 *
 */
package com.mytest.app.activities.dao;

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
import com.mytest.app.users.model.UsersActivitiesRef;

/**
 * @author yqwu
 *
 */
@Transactional
public class ActivityDAOImpl implements ActivityDAO {

	private SessionFactory sessionFactory;

	public ActivityDAOImpl(	SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Activity getActivity(Long activityId) {
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		Activity activity = null;
		try {
			// if (!session.getTransaction().isActive()) {
			// session.beginTransaction();
			// }
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			System.out.println("IN get Activity");
			activity = (Activity) session.get(Activity.class, activityId);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return activity;
	}

	public List<Activity> getActivities() {
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		List<Activity> activities = null;
		try {
			// session.beginTransaction();
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			System.out.println("IN LIST");
			Query query = session
					.createQuery("from Activity a where a.startTime >= now() order by id desc");
			query.setMaxResults(15);
			activities = query.list();
			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return activities;
	}

	@Transactional
	public int add(Activity activity) {
		int flag = 0;
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		try {
			// session.beginTransaction();
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			session.save(activity);
			session.getTransaction().commit();
			flag = 1;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return flag;
	}

	public List<Long> getActivityIdsOfUser(User user) {
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		List<Long> result = new ArrayList<Long>();
		String activityIdsSql = "select activity_id from fj_users_activities_assignment "
				+ "where user_id=" + user.getId();
		try {
			// session.beginTransaction();
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			Query query = session.createSQLQuery(activityIdsSql);
			result = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return result;
	}

	public List<Activity> getJoinedActivitiesOfUser(User user) {
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		List<Activity> result = new ArrayList<Activity>();
		String activitiesSql = "from Activity a where a.id in "
				+ "(select activityId from UsersActivitiesRef ua where ua.userId = "
				+ user.getId() + ") "
				+ "and a.startTime >= now() order by a.id desc";
		try {
			// session.beginTransaction();
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			Query query = session.createQuery(activitiesSql);
			result = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return result;
	}

	public List<Activity> getNotJoinedActivitiesOfUser(User user) {
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		List<Activity> result = new ArrayList<Activity>();
		String activitiesSql = "from Activity a where a.id not in "
				+ "(select activityId from UsersActivitiesRef ua where ua.userId = "
				+ user.getId() + ") "
				+ "and a.startTime >= now() order by a.id desc";
		try {
			// session.beginTransaction();
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			Query query = session.createQuery(activitiesSql);
			result = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return result;
	}

	public int joinActivity(Long userId, Long activityId) {
		int flag = 0;
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		UsersActivitiesRef userActivity = new UsersActivitiesRef();
		userActivity.setActivityId(activityId);
		userActivity.setUserId(userId);
		userActivity.setCreatedAt(new Date());
		try {
			// session.beginTransaction();
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			session.save(userActivity);
			session.getTransaction().commit();
			flag = 1;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return flag;
	}

	public int leaveActivity(Long userId, Long activityId) {
		int flag = 0;
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		String sql = "delete from UsersActivitiesRef where userId=" + userId
				+ " and activityId=" + activityId;
		try {
			// session.beginTransaction();
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			Query query = session.createQuery(sql);
			flag = query.executeUpdate();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return flag;
	}

	public UsersActivitiesRef getRef(Long userId, Long activityId) {
		Session session = sessionFactory.getCurrentSession().isOpen() ? sessionFactory
				.getCurrentSession() : sessionFactory.openSession();
		String sql = "from UsersActivitiesRef where userId=" + userId
				+ " and activityId=" + activityId;
		UsersActivitiesRef ref = null;
		try {
			// session.beginTransaction();
			if (!session.getTransaction().isActive()) {
				session.getTransaction().begin();
			} else {
				session.flush();
			}
			Query query = session.createQuery(sql);
			ref = (UsersActivitiesRef) query.uniqueResult();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return ref;
	}
}

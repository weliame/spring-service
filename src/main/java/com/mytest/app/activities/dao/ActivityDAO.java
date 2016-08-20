/**
 *
 */
package com.mytest.app.activities.dao;

import java.util.List;

import com.mytest.app.activities.model.Activity;
import com.mytest.app.users.model.User;
import com.mytest.app.users.model.UsersActivitiesRef;

/**
 * @author yqwu
 *
 */
public interface ActivityDAO {

	public Activity getActivity(Long activityId);

	public List<Activity> getActivities();

	public int add(Activity activity);

	public int joinActivity(Long userId, Long activityId);

	public int leaveActivity(Long userId, Long activityId);

	public List<Long> getActivityIdsOfUser(User user);

	public List<Activity> getJoinedActivitiesOfUser(User user);

	public List<Activity> getNotJoinedActivitiesOfUser(User user);

	public UsersActivitiesRef getRef(Long userId, Long activityId);
}

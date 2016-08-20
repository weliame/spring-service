/**
 *
 */
package com.mytest.app.users.dao;

import java.util.List;

import com.mytest.app.activities.model.Activity;
import com.mytest.app.users.model.User;

/**
 * @author yqwu
 *
 */
public interface UserDAO {

	public User getUser(Long id);

	public User getUser(String userName, Long mobile);

	public List<User> getJoinedUsersOfActivity(Activity activity);

	public List<User> getNotJoinedUsersOfActivity(Activity activity);

	int add(User user);

	int update(User user);
}

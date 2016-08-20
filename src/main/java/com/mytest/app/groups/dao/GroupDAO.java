/**
 *
 */
package com.mytest.app.groups.dao;

import java.util.List;

import com.mytest.app.groups.model.Group;

/**
 * @author yqwu
 *
 */
public interface GroupDAO {

	public Group getGroup(Long activityId);

	public List<Group> getGroups();

}

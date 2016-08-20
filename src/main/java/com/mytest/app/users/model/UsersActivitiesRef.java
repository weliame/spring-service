package com.mytest.app.users.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "fj_users_activities_assignment")
public class UsersActivitiesRef {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "activity_id")
	private Long activityId;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "created_at")
	private Date createdAt;

	public UsersActivitiesRef() {
	}

	public UsersActivitiesRef(	Long id, Long userId, Long activityId,
								Date createdAt) {
		this.id = id;
		this.userId = userId;
		this.activityId = activityId;
		this.createdAt = createdAt;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the activityId
	 */
	public Long getActivityId() {
		return activityId;
	}

	/**
	 * @param activityId
	 *            the activityId to set
	 */
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}

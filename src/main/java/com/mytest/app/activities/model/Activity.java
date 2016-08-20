package com.mytest.app.activities.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "fj_activities")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity implements Serializable {

	private static final long serialVersionUID = -7968390920159511904L;

	@Id
	@GeneratedValue
	private Long id;

	@NotEmpty
	@Size(max = 256)
	private String title;

	@Column(name = "start_time")
	// @NotEmpty
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	@Column(name = "end_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	@Size(max = 2048)
	private String content;

	@Column(name = "group_id")
	private Long groupId;

	@CreatedBy
	@Column(name = "created_by")
	private String createdBy;

	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "created_at")
	private Date createdAt;

	public Activity() {
	}

	public Activity(Long id, String title, Date startTime, Date endTime,
					String content, long groupId, String createdBy,
					Date createdAt) {
		this.id = id;
		this.title = title;
		this.startTime = startTime;
		this.endTime = endTime;
		this.content = content;
		this.groupId = groupId;
		this.createdBy = createdBy;
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
	public void setId(	Long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(	String title) {
		this.title = title;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(	Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(	Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(	String content) {
		this.content = content;
	}

	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(	Long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(	String createdBy) {
		this.createdBy = createdBy;
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
	public void setCreatedAt(	Date createdAt) {
		this.createdAt = createdAt;
	}
}

package com.example.demo.domain;

import java.util.Date;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 08:30
 * @Description: 状态机实体类
 */

public class StateMachineEntity {

	private Integer id;

	private Integer currentStateId;

	private String description;

	private Date createTime;

	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCurrentStateId() {
		return currentStateId;
	}

	public void setCurrentStateId(Integer currentStateId) {
		this.currentStateId = currentStateId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}

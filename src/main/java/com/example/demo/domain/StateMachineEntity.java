package com.example.demo.domain;

import java.util.Date;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 08:30
 * @Description: 状态机实体类
 */

public class StateMachineEntity {

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getDefaultStateId() {
		return defaultStateId;
	}

	public void setDefaultStateId(Integer defaultStateId) {
		this.defaultStateId = defaultStateId;
	}

	private Integer id;

	private String description;

	private Date createTime;

	private Integer defaultStateId;


}

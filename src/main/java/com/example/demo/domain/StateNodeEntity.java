package com.example.demo.domain;

import java.util.Date;
import java.util.List;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 08:34
 * @Description: 状态机节点
 */

public class StateNodeEntity {

	private Integer id;

	private Integer machineId;

	private String description;

	private String identification;

	private Date createTime;

	private Date updateTime;

	private List<ActionEntity> actions;

	public List<ActionEntity> getActions() {
		return actions;
	}

	public void setActions(List<ActionEntity> actions) {
		this.actions = actions;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMachineId() {
		return machineId;
	}

	public void setMachineId(Integer machineId) {
		this.machineId = machineId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
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

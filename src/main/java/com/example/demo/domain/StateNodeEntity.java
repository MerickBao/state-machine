package com.example.demo.domain;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-09 14:12
 * @Description: 状态节点实体类
 */

public class StateNodeEntity {

	private Integer id; // 状态节点id

	private Integer machineId; // 所属状态机id

	private String description; // 状态描述

	private String identification; // 状态标识

	private Date createTime; // 创建时间

	private Date updateTime; // 更新时间

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

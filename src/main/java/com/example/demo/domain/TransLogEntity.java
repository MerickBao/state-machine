package com.example.demo.domain;

import java.util.Date;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 08:37
 * @Description: 状态转移日志
 */

public class TransLogEntity {

	private Integer id;

	private Integer machineId;

	private Integer transId;

	private Date createTime;

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

	public Integer getTransId() {
		return transId;
	}

	public void setTransId(Integer transId) {
		this.transId = transId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}

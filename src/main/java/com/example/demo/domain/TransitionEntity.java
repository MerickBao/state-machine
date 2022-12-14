package com.example.demo.domain;

import java.util.Date;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 08:40
 * @Description: 转移实体类
 */

public class TransitionEntity {

	private Integer id;

	private Integer machineId;

	private Integer eventId;

	private Integer prev;

	private Integer next;

	private Date createTime;

	private Date updateTime;

	private EventEntity event;

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

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getPrev() {
		return prev;
	}

	public void setPrev(Integer prev) {
		this.prev = prev;
	}

	public Integer getNext() {
		return next;
	}

	public void setNext(Integer next) {
		this.next = next;
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

	public EventEntity getEvent() {
		return event;
	}
	public void setEvent(EventEntity event) {
		this.event = event;
	}
}

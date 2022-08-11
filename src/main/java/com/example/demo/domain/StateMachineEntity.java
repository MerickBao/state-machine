package com.example.demo.domain;

import com.example.demo.domain.StateNodeEntity;
import com.example.demo.domain.TransitionEntity;
import java.util.Date;
import java.util.List;
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

	private List<StateNodeEntity> stateNodes;

	private List<TransitionEntity> transitions;

	private String defaultState;

	public String getDefaultState() {
		return defaultState;
	}

	public void setDefaultState(String defaultState) {
		this.defaultState = defaultState;
	}

	public List<StateNodeEntity> getStateNodes() {
		return stateNodes;
	}

	public void setStateNodes(List<StateNodeEntity> stateNodes) {
		this.stateNodes = stateNodes;
	}

	public List<TransitionEntity> getTransitions() {
		return transitions;
	}

	public void setTransitions(List<TransitionEntity> transitions) {
		this.transitions = transitions;
	}

}

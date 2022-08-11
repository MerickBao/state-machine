package com.example.demo.service;

import com.example.demo.dao.StateMachineDAO;
import com.example.demo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 08:49
 * @Description:
 */

@Service
public class StateMachineService {

	@Autowired
	private StateMachineDAO stateMachineDAO;

	@Autowired
	private StateNodeService stateNodeService;

	@Autowired
	private TransitionService transitionService;

	public StateMachineEntity getStateMachineById(Integer id) {
		return stateMachineDAO.getStateMachineById(id);
	}

	public StateMachineEntity getStructById(Integer id){
		StateMachineEntity schema = stateMachineDAO.getStateMachineById(id);
		if (schema == null) return null;
		Integer schemaId = schema.getId();
		schema.setStateNodes(stateNodeService.getStateNodes(schemaId));
		schema.setTransitions(transitionService.getTransitions(schemaId));
		return schema;
	}

	public List<StateMachineEntity> getStateMachines() {
		return stateMachineDAO.getStateMachines();
	}
}

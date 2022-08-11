package com.example.demo.service;

import com.example.demo.dao.StateMachineDAO;
import com.example.demo.domain.*;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StateNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Autowired
	private ActionService actionService;

	@Autowired
	private EventService eventService;

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

	public int insertStateMachine(StateMachineEntity schema) {
		schema.setDefaultStateId(0);
		Integer machineId = stateMachineDAO.insertStateMachine(schema);
		schema.setId(machineId);
		List<TransitionEntity> trans = schema.getTransitions();
		List<StateNodeEntity> nodes = schema.getStateNodes();
		Map<String, Integer> map = new HashMap<>();
		for (StateNodeEntity node : nodes) {
			node.setMachineId(machineId);
			Integer nodeId = stateNodeService.insertNode(node);
			List<ActionEntity> actions = node.getActions();
			for (ActionEntity action : actions) {
				action.setNodeId(nodeId);
				actionService.insertAction(action);
			}
			map.put(node.getIdentification(), nodeId);
		}
		schema.setDefaultStateId(map.get(schema.getDefaultState()));
		stateMachineDAO.updateStateMachine(schema);
		for (TransitionEntity t : trans) {
			t.setMachineId(machineId);
			Integer eventId = eventService.insertEvent(t.getEvent());
			t.setEventId(eventId);
			t.setPrev(map.get(t.getPrevNode()));
			t.setNext(map.get(t.getNextNode()));
			transitionService.insertTransition(t);
		}
		return 0;
	}

	public List<StateMachineEntity> getStateMachines() {
		return stateMachineDAO.getStateMachines();
	}
}

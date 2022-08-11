package com.example.demo.service;

import com.example.demo.dao.StateNodeDAO;
import com.example.demo.domain.ActionEntity;
import com.example.demo.domain.StateNodeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 10:16
 * @Description:
 */

@Service
public class StateNodeService {

	@Autowired
	private StateNodeDAO stateNodeDAO;

	@Autowired
	private ActionService actionService;

	public StateNodeEntity getStateNodeById(Integer id) {
		StateNodeEntity node = stateNodeDAO.getStateNodeById(id);
		if (node == null) return null;
		List<ActionEntity> actions = actionService.getActionsByNodeId(id);
		node.setActions(actions);
		return node;
	}

	public List<StateNodeEntity> getStateNodes(Integer machineId) {
		List<StateNodeEntity> nodes = stateNodeDAO.getStateNodes(machineId);
		for (StateNodeEntity node : nodes) {
			List<ActionEntity> actions = actionService.getActionsByNodeId(node.getId());
			node.setActions(actions);
		}
		return nodes;
	}
}

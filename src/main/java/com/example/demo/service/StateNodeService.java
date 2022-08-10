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
		StateNodeEntity stateNodeEntity = stateNodeDAO.getStateNodeById(id);
		List<ActionEntity> actionEntities = actionService.getActionsByNodeId(id);
		stateNodeEntity.setActionEntities(actionEntities);
		return stateNodeEntity;
	}
}

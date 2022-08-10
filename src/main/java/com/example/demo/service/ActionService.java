package com.example.demo.service;

import com.example.demo.dao.ActionDAO;
import com.example.demo.domain.ActionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 11:28
 * @Description: 
 */

@Service
public class ActionService {

	@Autowired
	private ActionDAO actionDAO;

	public List<ActionEntity> getActionsByNodeId(Integer nodeId) {
		return actionDAO.getActionsByNodeId(nodeId);
	}
}

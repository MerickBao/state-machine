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

	public StateMachineEntity getStateMachineById(Integer id) {
		return stateMachineDAO.getStateMachineById(id);
	}

	public List<StateMachineEntity> getStateMachines() {
		return stateMachineDAO.getStateMachines();
	}
}

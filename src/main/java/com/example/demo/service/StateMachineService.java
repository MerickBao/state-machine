package com.example.demo.service;

import com.example.demo.dao.StateMachineDAO;
import com.example.demo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.*;

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
		List<StateMachineEntity> stateMachineEntities = new ArrayList<>();
		//  查询所有的machineId
		List<Integer> stateMachineIds = stateMachineDAO.getStateMachineIds();
		// 逐个进行查询、装载
		for (Integer id : stateMachineIds) {
			stateMachineEntities.add(stateMachineDAO.getStateMachineById(id));
		}
		return stateMachineEntities;
	}
}

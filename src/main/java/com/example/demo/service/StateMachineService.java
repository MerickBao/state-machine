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

	@Autowired
	private StateNodeService stateNodeService;

	@Autowired
	private TransitionService transitionService;

	@Autowired
	private ActionService actionService;

	@Autowired
	private TransLogService transLogService;

	public StateMachineEntity getStateMachineById(Integer id) {
		return stateMachineDAO.getStateMachineById(id);
	}

	public void updateStateMachine(StateMachineEntity machine){stateMachineDAO.updateStateMachine(machine);}

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

	public int transfer(Integer instanceId, Integer eventId) {
		// 具体的转移流程
		InstanceEntity instance = getInstanceById(instanceId);
		StateMachineEntity machine = getStateMachineById(instance.getMachineId());
		Integer curNodeId = instance.getCurrentStateId();
		// 根据当前结点和事件ID，查询TransitionEntity
		TransitionEntity trans = transitionService.getTrans(curNodeId, eventId);
		// 若不存在对应的Transition
		if (trans == null) return 1;
		// 获取下一个结点
		Integer nextNodeId = trans.getNext();
		// 改变当前结点
		instance.setCurrentStateId(nextNodeId);
		updateInstance(instance);
		// 进入新节点后，执行该结点包含的所有动作
		List<ActionEntity> actions = actionService.getActionsByNodeId(nextNodeId);
		for (ActionEntity action : actions) {
			actionService.applyAction(action);
		}
		TransLogEntity log = new TransLogEntity();
		log.setInstanceId(instanceId);
		log.setTransId(trans.getId());
		transLogService.addLog(log);
		return 0;
	}

	public List<TransitionEntity> getTransChain(Integer machineId) {
		// 查询machineId下的所有log
		List<TransLogEntity> logs = transLogService.getTransLogByMachineId(machineId);
		// 对应的转移实例列表
		List<TransitionEntity> trans = new ArrayList<>();
		// 查询对应的transEntity并加入List
		for (TransLogEntity log : logs) {
			trans.add(transitionService.getTransById(log.getTransId()));
		}
		// 状态机还没有进行过转移, 打印初始节点
		if (trans.size() == 0) {
			StateMachineEntity stateMachineEntity = stateMachineDAO.getStateMachineById(machineId);
			System.out.println(stateNodeService.getStateNodeById(stateMachineEntity.getCurrentStateId()).getDescription());
			return trans;
		}
		// 输出历史转移图
		for (TransitionEntity t : trans) {
			System.out.print(stateNodeService.getStateNodeById(t.getPrev()).getDescription() + "--->");
		}
		System.out.println(stateNodeService.getStateNodeById(trans.get(trans.size() - 1).getNext()).getDescription());
		return trans;
	}

	public void resetStateMachine(Integer machineId, Integer stateId) {
		stateMachineDAO.resetStateMachine(machineId, stateId);
		transLogService.resetTransLogByMachineId(machineId);
	}
}

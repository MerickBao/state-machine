package com.example.demo.service;

import com.example.demo.dao.StateMachineInstanceDAO;
import com.example.demo.domain.StateMachineEntity;
import com.example.demo.domain.StateNodeEntity;
import com.example.demo.domain.TransitionEntity;
import com.example.demo.domain.ActionEntity;
import com.example.demo.domain.InstanceEntity;
import com.example.demo.domain.TransLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 08:49
 * @Description:
 */

@Service
public class StateMachineInstanceService {

	@Autowired
	private StateMachineInstanceDAO stateMachineInsanceDAO;

	@Autowired
	private StateMachineService stateMachineService;

	@Autowired
	private StateNodeService stateNodeService;

	@Autowired
	private TransitionService transitionService;

	@Autowired
	private ActionService actionService;

	@Autowired
	private TransLogService transLogService;

	public void updateInstance(InstanceEntity instance) {
		stateMachineInstanceDAO.updateInstance(instance);
	}

	public int transfer(Integer instanceId, Integer eventId) {
		// 具体的转移流程
		InstanceEntity instance = stateMachineInstanceDAO.getStateMachineInstanceById(instanceId);
		StateMachineEntity machine = stateMachineService.getStateMachineById(instance.getMachineId());
		Integer curNodeId = instance.getCurrentStateId();
		// 根据当前结点和事件ID，查询TransitionEntity
		TransitionEntity trans = transitionService.getTrans(curNodeId, eventId);
		// 若不存在对应的Transition
		if (trans == null) return 1;
		// 获取下一个结点
		Integer nextNodeId = trans.getNext();
		// 改变当前结点
		instance.setCurrentStateId(nextNodeId);
		this.updateInstance(instance);
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
		List<TransLogEntity> logs = transLogService.getTransLogByInstanceId(machineId);
		// 对应的转移实例列表
		List<TransitionEntity> trans = new ArrayList<>();
		// 查询对应的transEntity并加入List
		for (TransLogEntity log : logs) {
			trans.add(transitionService.getTransById(log.getTransId()));
		}
		// 状态机还没有进行过转移, 打印初始节点
		if (trans.size() == 0) {
			InstanceEntity instance = stateMachineInstanceDAO.getStateMachineInstanceById(machineId);
			System.out.println(stateNodeService.getStateNodeById(instance.getCurrentStateId()).getDescription());
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
		stateMachineInstanceDAO.resetStateMachine(machineId, stateId);
		transLogService.resetTransLogByInstanceId(machineId);
	}
}

package com.example.demo.api;

import com.example.demo.domain.JsonResponse;
import com.example.demo.domain.StateMachineEntity;
import com.example.demo.domain.TransitionEntity;
import com.example.demo.service.StateMachineService;
import com.example.demo.service.TransitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 08:46
 * @Description:
 */

@RestController
public class StateMachineApi {

	@Autowired
	private StateMachineService stateMachineService;

	@Autowired
	private TransitionService transitionService;

	// 查询单个状态机
	@GetMapping("/state-machine")
	public JsonResponse<StateMachineEntity> getStateMachineById(@RequestParam Integer machineId) {
		StateMachineEntity stateMachineEntity = stateMachineService.getStateMachineById(machineId);
		return new JsonResponse<>(stateMachineEntity);
	}

	// 查询所有状态机
	@GetMapping("/state-machines")
	public JsonResponse<List<StateMachineEntity>> getStateMachines() {
		List<StateMachineEntity> stateMachineEntities = stateMachineService.getStateMachines();
		return new JsonResponse<>(stateMachineEntities);
	}

	// 查询一个状态机结构的所有转移
	@GetMapping("/transitions")
	public JsonResponse<List<TransitionEntity>> getTransitions(@RequestParam Integer machineId) {
		List<TransitionEntity> transitions = transitionService.getTransitions(machineId);
		return new JsonResponse<>(transitions);
	}
}

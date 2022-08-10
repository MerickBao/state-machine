package com.example.demo.api;

import com.example.demo.domain.JsonResponse;
import com.example.demo.domain.StateMachineEntity;
import com.example.demo.domain.TransLogEntity;
import com.example.demo.domain.TransitionEntity;
import com.example.demo.service.StateMachineService;
import com.example.demo.service.TransLogService;
import com.example.demo.service.TransitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	private TransLogService transLogService;

	@Autowired
	private TransitionService transitionService;

	// 查询单个状态机
	@GetMapping("/state-machine")
	public JsonResponse<StateMachineEntity> getStateMachineById(@RequestParam Integer id) {
		StateMachineEntity stateMachineEntity = stateMachineService.getStateMachineById(id);
		return new JsonResponse<>(stateMachineEntity);
	}

	// 查询所有状态机
	@GetMapping("/state-machines")
	public JsonResponse<List<StateMachineEntity>> getStateMachines() {
		List<StateMachineEntity> stateMachineEntities = stateMachineService.getStateMachines();
		return new JsonResponse<>(stateMachineEntities);
	}

	// 查询状态机转移日志
	@GetMapping("/state-machine-trans-log")
	public JsonResponse<List<TransLogEntity>> getTransLogByMachineId(@RequestParam Integer id) {
		List<TransLogEntity> transLogEntities = transLogService.getTransLogByMachineId(id);
		return new JsonResponse<>(transLogEntities);
	}

	// 进行状态转移
	@GetMapping("/trans")
	public JsonResponse<String> transfer(@RequestParam Integer machineId, @RequestParam Integer code) {
		int res = stateMachineService.transfer(machineId, code);
		return res == 0 ? JsonResponse.success() : JsonResponse.fail();
	}

	@GetMapping("/transitions")
	public JsonResponse<List<TransitionEntity>> getTransitions(@RequestParam Integer machineId) {
		List<TransitionEntity> transitions = transitionService.getTransitions(machineId);
		return new JsonResponse<>(transitions);
	}

	@GetMapping("/printinfo")
	public JsonResponse<List<TransitionEntity>> printinfo(@RequestParam Integer machineId) {
		List<TransitionEntity> transChain = stateMachineService.getTransChain(machineId);
		return new JsonResponse<>(transChain);
	}

}

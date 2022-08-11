package com.example.demo.api;

import com.example.demo.domain.InstanceEntity;
import com.example.demo.domain.JsonResponse;
import com.example.demo.domain.TransLogEntity;
import com.example.demo.domain.TransitionEntity;
import com.example.demo.service.StateMachineInstanceService;
import com.example.demo.service.TransLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StateMachineInstanceApi {

	@Autowired
	private StateMachineInstanceService stateMachineInstanceService;

	@Autowired
	private TransLogService transLogService;

	// 增加状态机实例
	@PostMapping("/instance")
	public JsonResponse<String> insertInstance(@RequestBody InstanceEntity instance) {
		int res = stateMachineInstanceService.insertInstance(instance);
		return res == 0 ? JsonResponse.success() : JsonResponse.fail();
	}

	// 查询一个状态机结构下的所有示例
	@GetMapping("/instances")
	public JsonResponse<List<InstanceEntity>> getInstances(@RequestParam Integer machineId) {
		List<InstanceEntity> instances = stateMachineInstanceService.getInstances(machineId);
		return new JsonResponse<>(instances);
	}

	// 查询状态机转移日志
	@GetMapping("/instance-trans-log")
	public JsonResponse<List<TransLogEntity>> getTransLogByInstanceId(@RequestParam Integer id) {
		List<TransLogEntity> transLogEntities = transLogService.getTransLogByInstanceId(id);
		return new JsonResponse<>(transLogEntities);
	}

	// 进行状态转移
	@GetMapping("/trans")
	public JsonResponse<String> transfer(@RequestParam Integer id, @RequestParam Integer code) {
		int res = stateMachineInstanceService.transfer(id, code);
		return res == 0 ? JsonResponse.success() : JsonResponse.fail();
	}

	// 输出转移链
	@GetMapping("/printinfo")
	public JsonResponse<List<TransitionEntity>> printinfo(@RequestParam Integer id) {
		List<TransitionEntity> transChain = stateMachineInstanceService.getTransChain(id);
		return new JsonResponse<>(transChain);
	}

	// 重置状态机实例
	@GetMapping("/reset-instance")
	public JsonResponse<String> resetInstance(@RequestParam Integer id) {
		int res = stateMachineInstanceService.resetInstance(id);
		return res == 0 ? JsonResponse.success() : JsonResponse.fail();
	}
}

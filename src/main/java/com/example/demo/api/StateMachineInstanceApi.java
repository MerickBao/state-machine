package com.example.demo.api;

import com.example.demo.domain.JsonResponse;
import com.example.demo.domain.TransLogEntity;
import com.example.demo.domain.TransitionEntity;
import com.example.demo.service.StateMachineInstanceService;
import com.example.demo.service.TransLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StateMachineInstanceApi {

	@Autowired
	private StateMachineInstanceService stateMachineInstanceService;

	@Autowired
	private TransLogService transLogService;

	// 查询状态机转移日志
	@GetMapping("/state-machine-trans-log")
	public JsonResponse<List<TransLogEntity>> getTransLogByInstanceId(@RequestParam Integer id) {
		List<TransLogEntity> transLogEntities = transLogService.getTransLogByInstanceId(id);
		return new JsonResponse<>(transLogEntities);
	}

	// 进行状态转移
	@GetMapping("/trans")
	public JsonResponse<String> transfer(@RequestParam Integer instanceId, @RequestParam Integer code) {
		int res = stateMachineInstanceService.transfer(instanceId, code);
		return res == 0 ? JsonResponse.success() : JsonResponse.fail();
	}

	// 输出转移链
	@GetMapping("/printinfo")
	public JsonResponse<List<TransitionEntity>> printinfo(@RequestParam Integer instanceId) {
		List<TransitionEntity> transChain = stateMachineInstanceService.getTransChain(instanceId);
		return new JsonResponse<>(transChain);
	}

	// 重置状态机实例
	@GetMapping("/reset-machine")
	public JsonResponse<String> resetInstance(@RequestParam Integer instanceId) {
		int res = stateMachineInstanceService.resetInstance(instanceId);
		return res == 0 ? JsonResponse.success() : JsonResponse.fail();
	}
}

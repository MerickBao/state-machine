package com.example.demo.api;

import com.example.demo.domain.JsonResponse;
import com.example.demo.domain.StateNodeEntity;
import com.example.demo.service.StateNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-09 14:11
 * @Description: 状态节点API
 */

@RestController
public class StateNodeApi {

	@Autowired
	private StateNodeService stateNodeService;


	/**
	 * description: 根据状态id获取状态节点
	 * param：状态id
	 * **/
	@GetMapping("/state-node")
	public JsonResponse<StateNodeEntity> getStateNodeById(@RequestParam Integer id) {
		StateNodeEntity stateNodeEntity = stateNodeService.getStateNodeById(id);
		return new JsonResponse<>(stateNodeEntity);
	}

	/**
	 * description: 获取一个状态机所包含的所有状态
	 * param：状态机id
	 * **/
	@GetMapping("/state-nodes")
	public JsonResponse<List<StateNodeEntity>> getStateNodesByMachineId(@RequestParam Integer machineId) {
		List<StateNodeEntity> stateNodeEntities = stateNodeService.getStateNodesByMachineId(machineId);
		return new JsonResponse<>(stateNodeEntities);
	}

	/**
	 * description: 新建一个状态节点
	 * param：状态节点实体
	 * **/
	@PostMapping("/state-node")
	public JsonResponse<String> addStateNode(@RequestBody StateNodeEntity stateNodeEntity) {
		Integer code = stateNodeService.addStateNode(stateNodeEntity);
		if (code.equals(0)) return JsonResponse.success();
		return JsonResponse.fail();
	}

	/**
	 * description: 删除一个状态节点
	 * 调用方式： http://localhost:8080/state-node/9
	 * **/
	@DeleteMapping("/state-node/{nodeId}")
	public JsonResponse<String> deleteStateNodeById(@PathVariable(value = "nodeId") Integer nodeId) {
		stateNodeService.deleteStateNodeById(nodeId);
		return JsonResponse.success();
	}
}

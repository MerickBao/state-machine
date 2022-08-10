package com.example.demo.api;

import com.example.demo.domain.JsonResponse;
import com.example.demo.domain.StateNodeEntity;
import com.example.demo.service.StateNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 10:09
 * @Description:
 */

@RestController
public class StateNodeApi {

	@Autowired
	private StateNodeService stateNodeService;

	// 查询节点信息
	@GetMapping("/state-node")
	public JsonResponse<StateNodeEntity> getStateNodeById(@RequestParam Integer id) {
		StateNodeEntity stateNodeEntity = stateNodeService.getStateNodeById(id);
		return new JsonResponse<>(stateNodeEntity);
	}
}

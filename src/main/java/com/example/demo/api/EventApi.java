package com.example.demo.api;

import com.example.demo.domain.EventEntity;
import com.example.demo.domain.JsonResponse;
import com.example.demo.service.EventService;
import com.example.demo.service.StateMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 10:55
 * @Description:
 */

@RestController
public class EventApi {

	@Autowired
	private EventService eventService;

	@Autowired
	private StateMachineService stateMachineService;

	@GetMapping("/event")
	public JsonResponse<EventEntity> getEventById(@RequestParam Integer id) {
		EventEntity eventEntity = eventService.getEventById(id);
		return new JsonResponse<>(eventEntity);
	}

	@GetMapping("/event-notification")
	public JsonResponse<String> getEventById(@RequestParam Integer machineId, @RequestParam Integer code) {
		int res = stateMachineService.transfer(machineId, code);
		return res == 0 ? JsonResponse.success() : JsonResponse.fail();
	}
}

package com.example.demo.service;

import com.example.demo.dao.StateMachineDAO;
import com.example.demo.domain.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public StateMachineEntity getStateMachineById(Integer id) {
		return stateMachineDAO.getStateMachineById(id);
	}

	public StateMachineEntity getStructById(Integer id) {
		StateMachineEntity schema = stateMachineDAO.getStateMachineById(id);
		if (schema == null)
			return null;
		Integer schemaId = schema.getId();
		schema.setStateNodes(stateNodeService.getStateNodes(schemaId));
		schema.setTransitions(transitionService.getTransitions(schemaId));
		return schema;
	}

	public List<StateMachineEntity> getStateMachines() {
		return stateMachineDAO.getStateMachines();
	}

	static class RawTransition {
		@JsonIgnore
		public StateNodeEntity prev;

		@JsonIgnore
		public StateNodeEntity next;

		public String prevNode;
		public String nextNode;
		public EventEntity event;
	}

	class StateMachineBuilder {
		private final JsonNode machineSchema;
		private final Map<String, StateNodeEntity> nodeRegistry = new HashMap<>();
		private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		private int code;
		private StateMachineEntity schema;

		StateMachineBuilder(JsonNode machineSchema) {
			this.machineSchema = machineSchema;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public StateMachineEntity getSchema() {
			return schema;
		}

		private boolean register(String identification, StateNodeEntity stateNode) {
			return nodeRegistry.put(identification, stateNode) == null;
		}

		public void build() {
			schema = new StateMachineEntity();

			String description = machineSchema.get("description").textValue();
			if (description == null) {
				this.setCode(2);
				return;
			}
			schema.setDescription(description);

			String defaultStateId = machineSchema.get("defaultState").textValue();

			List<StateNodeEntity> stateNodes;
			try {
				stateNodes = mapper
					.readerForListOf(StateNodeEntity.class)
					.readValue(machineSchema.get("stateNodes"));
			} catch (IOException e) {
				this.setCode(3);
				return;
			}
			for (StateNodeEntity node : stateNodes) {
				if (!register(node.getIdentification(), node)) {
					this.setCode(4);
					return;
				}
			}

			StateNodeEntity defaultState = nodeRegistry.get(defaultStateId);
			if (defaultState == null) {
				this.setCode(5);
				return;
			}

			List<RawTransition> rawTransitions;
			try {
				rawTransitions = mapper
					.readerForListOf(RawTransition.class)
					.readValue(machineSchema.get("transitions"));
			} catch (IOException e) {
				this.setCode(6);
				return;
			}

			for (RawTransition rawTransition : rawTransitions) {
				if ((rawTransition.prev = nodeRegistry.get(rawTransition.prevNode)) == null) {
					this.setCode(7);
					return;
				}
				if ((rawTransition.next = nodeRegistry.get(rawTransition.nextNode)) == null) {
					this.setCode(8);
					return;
				}
			}

			// 全部读取完毕并验证成功，开始插入数据
			StateMachineDAO./* 插入 */(schema);
			for (StateNodeEntity node : stateNodes) {
				node.setMachineId(schema.getId());
				StateNodeDAO./* 插入 */(node);
				for (ActionEntity action : node.getActions()) {
					action.setNodeId(node.getId());
					ActionDAO./* 插入 */(action);
				}
			}

			for (RawTransition rawTransition : rawTransitions) {
				TransitionEntity transition = new TransitionEntity();
				transition.setPrev(rawTransition.prev.getId());
				transition.setNext(rawTransition.next.getId());
				EventDAO./* 插入 */(rawTransition.event);
				transition.setEventId(rawTransition.event.getId());
				TransitionDAO./* 插入 */(transitition);
			}
		}
	}

	public int createStateMachine(JsonNode machineSchema) {
		if (!machineSchema.isObject())
			return 1;

		StateMachineBuilder builder = new StateMachineBuilder(machineSchema);

		builder.build();
		System.out.printf("code = %s\n", builder.code);
		return builder.code;
	}
}

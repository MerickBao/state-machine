package com.example.demo.service;

import com.example.demo.dao.StateMachineDAO;
import com.example.demo.domain.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private ActionService actionService;

	@Autowired
	private EventService eventService;

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

	public Integer insertStateMachine(StateMachineEntity schema) {
		return stateMachineDAO.insertStateMachine(schema);
	}

	public void updateStateMachine(StateMachineEntity schema) {
		stateMachineDAO.updateStateMachine(schema);
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
		private StateMachineEntity schema;

		StateMachineBuilder(JsonNode machineSchema) {
			this.machineSchema = machineSchema;
		}

		public StateMachineEntity getSchema() {
			return schema;
		}

		private boolean register(String identification, StateNodeEntity stateNode) {
			return nodeRegistry.put(identification, stateNode) == null;
		}

		@Transactional
		public int build() {
			if (!machineSchema.isObject()) return 1;

			
			JsonNode descriptionField = machineSchema.get("description");
			if (descriptionField == null) return 2;
			String description = descriptionField.textValue();
			if (description == null) return 3;

			schema = new StateMachineEntity();
			schema.setDescription(description);
			schema.setDefaultStateId(-1);

			List<StateNodeEntity> stateNodes;
			try {
				stateNodes = mapper
					.readerForListOf(StateNodeEntity.class)
					.readValue(machineSchema.get("stateNodes"));
			} catch (IOException | IllegalArgumentException e) {
				return 4;
			}

			for (StateNodeEntity node : stateNodes) {
				if (node.getDescription() == null) return 5;
				if (node.getIdentification() == null) return 6;
				if (!register(node.getIdentification(), node)) return 7;
				if (node.getActions() == null) return 8;
				for (ActionEntity action : node.getActions()) {
					if (action.getUrl() == null) return 9;
				}
			}

			JsonNode defaultStateField = machineSchema.get("defaultState");
			if (defaultStateField == null) return 10;
			String defaultStateId = defaultStateField.textValue();
			StateNodeEntity defaultState = nodeRegistry.get(defaultStateId);
			if (defaultState == null) return 11;



			List<RawTransition> rawTransitions;
			try {
				rawTransitions = mapper
					.readerForListOf(RawTransition.class)
					.readValue(machineSchema.get("transitions"));
			} catch (IOException | IllegalArgumentException e) {
				return 12;
			}

			for (RawTransition rawTransition : rawTransitions) {
				if ((rawTransition.prev = nodeRegistry.get(rawTransition.prevNode)) == null) return 13;
				if ((rawTransition.next = nodeRegistry.get(rawTransition.nextNode)) == null) return 14;
				if (rawTransition.event == null) return 15;
				if (rawTransition.event.getDescription() == null) return 16;
				if (rawTransition.event.getCode() == null) return 17;
			}

			// 全部读取完毕并验证成功，开始插入数据
			insertStateMachine(schema);
			for (StateNodeEntity node : stateNodes) {
				node.setMachineId(schema.getId());
				stateNodeService.insertNode(node);
				for (ActionEntity action : node.getActions()) {
					action.setNodeId(node.getId());
					actionService.insertAction(action);
				}
			}
			schema.setDefaultStateId(defaultState.getId());
			updateStateMachine(schema);

			for (RawTransition rawTransition : rawTransitions) {
				TransitionEntity transition = new TransitionEntity();
				transition.setPrev(rawTransition.prev.getId());
				transition.setNext(rawTransition.next.getId());
				transition.setMachineId(schema.getId());
				eventService.insertEvent(rawTransition.event);
				transition.setEventId(rawTransition.event.getId());
				transitionService.insertTransition(transition);
			}
			return 0;
		}
	}

	public int createStateMachine(JsonNode machineSchema) {
		StateMachineBuilder builder = new StateMachineBuilder(machineSchema);

		return builder.build();
	}
}

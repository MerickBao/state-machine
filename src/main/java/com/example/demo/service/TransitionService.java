package com.example.demo.service;

import com.example.demo.dao.TransitionDAO;
import com.example.demo.domain.TransitionEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TransitionService {

	@Autowired
	private TransitionDAO transitionDAO;

	@Autowired
	private EventService eventService;

	public TransitionEntity getTransById(Integer id) {
		TransitionEntity trans = transitionDAO.getTransById(id);
		if (trans == null) return null;
		trans.setEvent(eventService.getEventById(trans.getEventId()));
		return trans;
	}

	public TransitionEntity getTrans(Integer curNodeId, Integer eventId) {
		TransitionEntity trans = transitionDAO.getTransition(curNodeId, eventId);
		if (trans == null) return null;
		trans.setEvent(eventService.getEventById(eventId));
		return trans;
	}
	public List<TransitionEntity> getTransitions(Integer machineId) {
		List<TransitionEntity> trans = transitionDAO.getTransitions(machineId);
		for (TransitionEntity t : trans) {
			t.setEvent(eventService.getEventById(t.getEventId()));
		}
		return trans;
	}
}

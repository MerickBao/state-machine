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

	public TransitionEntity getTransById(Integer id) {
		return transitionDAO.getTransById(id);
	}

	public TransitionEntity getTrans(Integer curNodeId, Integer eventId) {
		return transitionDAO.getTransition(curNodeId, eventId);
	}
	public List<TransitionEntity> getTransitions(Integer machineId) {
		return transitionDAO.getTransitions(machineId);
	}
}

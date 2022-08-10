package com.example.demo.service;

import com.example.demo.dao.TransitionDAO;
import com.example.demo.domain.TransitionEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransitionService {

	@Autowired
	private TransitionDAO transitionDAO;

	TransitionEntity getTransById(Integer id) {
		return transitionDAO.getTransById(id);
	}

	TransitionEntity getTrans(Integer curNodeId, Integer eventId) {
		return transitionDAO.getTransition(curNodeId, eventId);
	}
}

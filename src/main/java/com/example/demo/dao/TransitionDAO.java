package com.example.demo.dao;

import com.example.demo.domain.TransitionEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransitionDAO {
	TransitionEntity getTransById(Integer id);

	TransitionEntity getTransition(Integer nodeId, Integer eventId);
}

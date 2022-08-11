package com.example.demo.dao;

import com.example.demo.domain.TransitionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface TransitionDAO {
	TransitionEntity getTransById(Integer id);

	TransitionEntity getTransition(@Param("nodeId") Integer nodeId, @Param("eventId") Integer eventId);

	List<TransitionEntity> getTransitions(Integer machineId);

	Integer insertTransition(TransitionEntity trans);
}

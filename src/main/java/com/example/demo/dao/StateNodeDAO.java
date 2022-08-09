package com.example.demo.dao;

import com.example.demo.domain.StateNodeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-09 14:16
 * @Description:
 */

@Mapper
public interface StateNodeDAO {

	StateNodeEntity getStateNodeById(int id);

	Integer addStateNode(StateNodeEntity stateNodeEntity);

	List<StateNodeEntity> getStateNodesByMachineId(Integer machineId);

	void deleteStateNodeById(Integer nodeId);
}

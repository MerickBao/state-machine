package com.example.demo.dao;

import com.example.demo.domain.StateNodeEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 10:32
 * @Description:
 */

@Mapper
public interface StateNodeDAO {
	StateNodeEntity getStateNodeById(Integer id);

	List<StateNodeEntity> getStateNodes(Integer machineId);

	Integer insertNode(StateNodeEntity node);
}

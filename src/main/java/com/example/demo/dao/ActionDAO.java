package com.example.demo.dao;

import com.example.demo.domain.ActionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 11:29
 * @Description:
 */

@Mapper
public interface ActionDAO {
	List<ActionEntity> getActionsByNodeId(Integer nodeId);

	Integer insertAction(ActionEntity action);
}

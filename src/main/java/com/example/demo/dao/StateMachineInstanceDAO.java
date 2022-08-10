package com.example.demo.dao;

import com.example.demo.domain.InstanceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StateMachineInstanceDAO {
    InstanceEntity getStateMachineInstanceById(Integer id);

    void updateInstance(InstanceEntity instance);

    void resetInstance(@Param("instanceId") Integer instanceId, @Param("stateId") Integer stateId);
}

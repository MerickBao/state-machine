package com.example.demo.dao;

import com.example.demo.domain.StateMachineEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 08:50
 * @Description:
 */

@Mapper
public interface StateMachineDAO {
    StateMachineEntity getStateMachineById(Integer id);

    List<Integer> getStateMachineIds();

    void updateStateMachine(StateMachineEntity machine);

    void resetStateMachine(@Param("machineId") Integer machineId, @Param("stateId") Integer stateId);
}

package com.example.demo.service;

import com.example.demo.dao.StateNodeDAO;
import com.example.demo.domain.StateNodeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-09 14:14
 * @Description: 状态节点的具体业务逻辑实现
 */

@Service
public class StateNodeService {

    @Autowired
    private StateNodeDAO stateNodeDAO;

    public StateNodeEntity getStateNodeById(Integer id) {
        return stateNodeDAO.getStateNodeById(id);
    }

    public Integer addStateNode(StateNodeEntity stateNodeEntity) {
        if (stateNodeEntity.getMachineId() == null) return 1;
        stateNodeDAO.addStateNode(stateNodeEntity);
        return 0;
    }

    public List<StateNodeEntity> getStateNodesByMachineId(Integer machineId) {
        if (machineId == null) return new ArrayList<>();
        return stateNodeDAO.getStateNodesByMachineId(machineId);
    }

    public void deleteStateNodeById(Integer nodeId) {
        stateNodeDAO.deleteStateNodeById(nodeId);
    }
}

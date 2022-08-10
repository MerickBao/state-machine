package com.example.demo.dao;

import com.example.demo.domain.TransLogEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 09:14
 * @Description:
 */

@Mapper
public interface TransLogDAO {
	List<TransLogEntity> getTransLogByMachineId(Integer machineId);
}

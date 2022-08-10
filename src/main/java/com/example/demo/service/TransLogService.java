package com.example.demo.service;

import com.example.demo.dao.TransLogDAO;
import com.example.demo.domain.TransLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-10 09:13
 * @Description:
 */

@Service
public class TransLogService {

	@Autowired
	private TransLogDAO transLogDAO;

	public List<TransLogEntity> getTransLogByMachineId(Integer machineId) {
		return transLogDAO.getTransLogByMachineId(machineId);
	}
}

package com.example.demo.service;

import com.example.demo.dao.TransLogDAO;
import com.example.demo.domain.TransLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransLogService {
	@Autowired
	private TransLogDAO transLogDAO;

	public List<TransLogEntity> getTransLogByMachineId(Integer machineId) {
		return transLogDAO.getTransLogByMachineId(machineId);
	}

	public int addLog(TransLogEntity log) {
		return transLogDAO.addLog(log);
	}

	public void resetTransLogByMachineId(Integer machineId) {
		transLogDAO.resetTransLogByMachineId(machineId);
	}
}

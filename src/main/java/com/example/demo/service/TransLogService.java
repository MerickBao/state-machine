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

	public List<TransLogEntity> getTransLogByInstanceId(Integer instanceId) {
		return transLogDAO.getTransLogByInstanceId(instanceId);
	}

	public int addLog(TransLogEntity log) {
		return transLogDAO.addLog(log);
	}

	public void resetTransLogByInstanceId(Integer instanceId) {
		transLogDAO.resetTransLogByInstanceId(instanceId);
	}
}

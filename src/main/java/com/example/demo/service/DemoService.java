package com.example.demo.service;

import com.example.demo.dao.DemoDAO;
import com.example.demo.domain.DemoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-09 08:54
 * @Description: 用于测试的service层: 业务逻辑的具体实现
 */

@Service
public class DemoService {

	@Autowired
	private DemoDAO demoDAO;


	public DemoEntity newDemoEntity() {

		// 具体的信息通常在数据库中查出来
		DemoEntity demoEntity = new DemoEntity();
		demoEntity.setId(1);
		demoEntity.setName("alice");
		List<String> skills = new ArrayList<>();
		skills.add("c++");
		skills.add("python");
		skills.add("Java");
		demoEntity.setSkills(skills);
		return demoEntity;
	}

	public DemoEntity getDemoEntityById(int id) {
		return demoDAO.getDemoEntityById(id);
	}
}

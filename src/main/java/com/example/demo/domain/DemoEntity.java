package com.example.demo.domain;

import java.util.List;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-09 09:08
 * @Description: 用于测试的实体类
 */

public class DemoEntity {

	private int id;

	private String name;

	private List<String> skills;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}
}

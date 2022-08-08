package com.example.demo.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-08 15:42
 * @Description: 测试服务是否正常启动
 */

@RestController
public class DemoApi {

	@GetMapping("/test-query")
	public String testQuery() {
		return "hello world!";
	}

	@GetMapping("/qaq")
	public String testQuery2() {
		return "hello world2!!!!!!";
  }

	@GetMapping("/test-query-1")
	public String testQuery1() {
		return "test-query-1";
	}
}

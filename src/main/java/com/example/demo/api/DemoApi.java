package com.example.demo.api;

import com.example.demo.domain.DemoEntity;
import com.example.demo.domain.JsonResponse;
import com.example.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: merickbao
 * @Created_Time: 2022-08-08 15:42
 * @Description: 测试服务是否正常启动
 */

@RestController
public class DemoApi {

	@Autowired
	private DemoService demoService;

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

	@GetMapping("/json-response-test")
	public JsonResponse<String> jsonResponseTest() {
		return new JsonResponse<>("jsonResponseTest");
	}

	/**
	 * @ description: xxx
	 * @ param: param1 : xxx
	 * @ param: param2 : xxx
	 *
	 * */
	@GetMapping("/json-response-test-param")
	public JsonResponse<String>  jsonResponseTestWithParam(@RequestParam String param) {
		return new JsonResponse<>(param);
	}

	@GetMapping("/json-response-test-service")
	public JsonResponse<DemoEntity> jsonResponseTestWithService() {
		DemoEntity demoEntity = demoService.newDemoEntity();
		return new JsonResponse<>(demoEntity);
	}

	@GetMapping("/json-response-test-dao")
	public JsonResponse<DemoEntity> jsonResponseTestWithDAO(@RequestParam int id) {
		DemoEntity demoEntity = demoService.getDemoEntityById(id);
		return new JsonResponse<>(demoEntity);
	}
}

package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: merickbao
 * @Created_Time: 2023-07-30 18:23
 * @Description:
 */

@Configuration
@Slf4j
public class WebSocketConfig extends ServerEndpointConfig.Configurator {

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		Map<String, Object> userProperties = sec.getUserProperties();
		Map<String, List<String>> headers = request.getHeaders();
		System.out.println("headers");
		System.out.println(headers.toString());
		userProperties.put("host-test", headers.get("host").get(0));
	}
}

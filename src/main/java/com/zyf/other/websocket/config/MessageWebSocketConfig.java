/*
package com.zyf.other.websocket.config;

import com.zyf.other.websocket.config.properties.WebSocketProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@ComponentScan(basePackages = "com.zyf.other.websocket.web")
@EnableConfigurationProperties(value = WebSocketProperties.class)
public class MessageWebSocketConfig implements WebSocketConfigurer {

	@Autowired
	private WebSocketProperties webSocket;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		if (StringUtils.isNotBlank(this.webSocket.getHandlerUri()))
			for (String uri : this.webSocket.getHandlerUri().split(";")) {
				registry
						.addHandler(messageWebSocketHandler(), uri)
						.addInterceptors(new MessageWebSocketInterceptor()).withSockJS();
			}
	}

	@Bean
	public MessageWebSocketHandler messageWebSocketHandler() {
		return new MessageWebSocketHandler();
	}
}*/

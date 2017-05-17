/*
package com.zyf.other.websocket.config;

import com.zyf.other.websocket.utils.WebSocketUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class MessageWebSocketHandler implements WebSocketHandler {

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		System.out.println("afterConnectionEstablished");
		WebSocketUtils.getUsers(session).add(session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
		System.out.println("handleMessage : " + session.getId() + " send " + message.getPayload().toString());
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) {
		System.out.println("handleTransportError");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
		System.out.println("afterConnectionClosed");
		WebSocketUtils.getUsers(session).remove(session);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}


}*/

/*
package com.zyf.other.websocket.utils;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * Created by zengyufei on 2016/11/24.
 *//*

public class WebSocketUtils {

	private static Map<String, List<WebSocketSession>> groups;

	static {
		groups = new HashMap<>();
	}

	public static void addSession(WebSocketSession session){
		String namespace = getNamespace(session);
		List<WebSocketSession> users = groups.get(namespace);
		if(users.isEmpty()){
			users = new ArrayList<>();
			groups.put(namespace, users);
		}
		users.add(session);
	}

	public static List<WebSocketSession> getAllUsers() {
		ArrayList<WebSocketSession> allUsers = new ArrayList<>();
		groups.forEach((k, v) -> allUsers.addAll(v));
		return allUsers;
	}

	public static List<WebSocketSession> getUsers(WebSocketSession session) {
		String namespace = getNamespace(session);
		List<WebSocketSession> users;
		if (groups.containsKey(namespace))
			users = groups.get(namespace);
		else
			groups.put(namespace, users = new ArrayList<>());
		return users;
	}

	public static String getNamespace(WebSocketSession session) {
		String uri = session.getUri().getPath();
		return uri.substring(1, uri.indexOf("/", uri.indexOf("/", 1) + 1));
	}

	*/
/**
	 * 给所有在线用户发送消息
	 *
	 * @param result
	 *//*

	public static void sendMessageToAllUsers(String result) {
		for (WebSocketSession user : getAllUsers()) {
			try {
				if (user.isOpen())
					user.sendMessage(new TextMessage(result));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	*/
/**
	 * 给组内所有用户发送消息
	 *
	 * @param result
	 *//*

	public static void sendMessageToGroup(String namespace, String result) {
		List<WebSocketSession> groupAllUsers = groups.get(namespace);
		for (WebSocketSession user : groupAllUsers) {
			try {
				if (user.isOpen()) {
					user.sendMessage(new TextMessage(result));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	*/
/**
	 * 给某个用户发送消息
	 *
	 * @param userName
	 * @param result
	 *//*

	public static void sendMessageToUser(String userName, String result) {
		for (WebSocketSession user : getAllUsers()) {
			if (user.getId().equals(userName)) {
				try {
					if (user.isOpen()) {
						user.sendMessage(new TextMessage(result));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
}
*/

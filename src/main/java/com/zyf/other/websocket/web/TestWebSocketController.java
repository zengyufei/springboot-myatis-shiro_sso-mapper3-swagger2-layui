/*
package com.zyf.other.websocket.web;

import com.zyf.other.websocket.utils.WebSocketUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/websocket")
@Api("websocket测试")
public class TestWebSocketController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@ApiOperation(value = "后台通知前台",protocols = "ws",notes = "/websocket/server/send, 发送消息haha给所有websocket用户")
	@RequestMapping(value = "/server/send", method = RequestMethod.GET)
	@ResponseBody
	public void send() {
		System.out.println("send haha");
		WebSocketUtils.sendMessageToAllUsers("haha");
	}

	@ApiOperation(value = "客户端访问",protocols = "ws",notes = "直接访问/websocket/server/accept，通过SockJS连接")
	@RequestMapping(value = "/server/accept", method = RequestMethod.GET)
	public String accept() {
		return "test/websocket/test";
	}
}
*/

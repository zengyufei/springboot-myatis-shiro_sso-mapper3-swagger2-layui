/*
package com.zyf.other.web.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("test")
@Api(value = "测试使用的接口", description = "测试接口都写这")
public class TestMQController {


	@Autowired
	private AmqpTemplate rabbitTemplate;
	@Autowired
	private Sender sender;

	@ApiOperation(value = "测试MQ访问方式1", notes = "通过直接访问的方式，跟/mq2访问不同在于使用代码的方式上")
	@ApiResponse(code = 200, message = "返回传输的方式及时间")
	@RequestMapping(value = "/mq", method = RequestMethod.GET)
	@ResponseBody
	public String send() {
		String context = "default hello " + new Date();
		this.rabbitTemplate.convertAndSend("hello", context);
		return "Sender default : " + context;
	}

	@ApiOperation(value = "测试MQ访问方式2", notes = "通过直接访问的方式，跟/mq访问不同在于使用代码的方式上")
	@ApiResponse(code = 200, message = "返回传输的方式及时间")
	@RequestMapping(value = "/mq2", method = RequestMethod.GET)
	@ResponseBody
	public String send2() {
		String content = sender.send("controller");
		return content;
	}
}
*/

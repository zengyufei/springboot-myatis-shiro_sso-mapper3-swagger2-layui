/*
package com.zyf.other.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Sender {
	@Autowired
	private AmqpTemplate rabbitTemplate;
	public String send(String flag) {
		String context = flag + " hello " + new Date();
		this.rabbitTemplate.convertAndSend("hello", context);
		return "Sender "+ flag + " : " + context;
	}


}*/

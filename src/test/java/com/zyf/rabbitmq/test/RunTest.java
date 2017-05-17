/*
package com.zyf.rabbitmq.test;

import com.zyf.Application;
import com.zyf.other.rabbitmq.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ComponentScan(basePackages = "com.zyf.other.rabbitmq")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RunTest {
	@Autowired
	private Sender sender;
	@Test
	public void hello() throws Exception {
		String content = sender.send("runTest");
		System.out.println(content);
	}
}*/

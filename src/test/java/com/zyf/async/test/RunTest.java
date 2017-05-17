package com.zyf.async.test;

import com.zyf.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;


@EnableAsync    //开启异步处理
@ComponentScan(basePackageClasses = AsyncTask.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RunTest {

	@Autowired
	private AsyncTask asyncTask;

	@Test
	public void test() throws Exception {
		asyncTask.doTaskOne();
		asyncTask.doTaskTwo();
		asyncTask.doTaskThree();
		//如果上面都是异步的话，那么主线程junit test会一路执行到底，甚至是主线程在执行完毕，子线程被迫强制打断
		//所以需要在这里设置卡个10秒
		System.out.println("");
		System.out.println("---------------------------------------------");
		System.out.println("等待10秒");
		Thread.sleep(10000);
	}
}

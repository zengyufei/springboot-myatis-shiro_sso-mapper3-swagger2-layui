package com.zyf.async.test;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by zengyufei on 2016/11/25.
 */
@Component
public class AsyncTask {

	public static Random random = new Random();

	@Async
	public void doTaskOne(){
		System.out.println("开始做任务一");
		long start = System.currentTimeMillis();
		try {
			Thread.sleep(random.nextInt(10000));
		}catch(InterruptedException ex){
			System.out.println("错误1");
		}
		long end = System.currentTimeMillis();
		System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
	}

	@Async
	public void doTaskTwo(){
		System.out.println("开始做任务二");
		long start = System.currentTimeMillis();
		try {
			Thread.sleep(random.nextInt(10000));
		}catch(InterruptedException ex){
			System.out.println("错误2");
		}
		long end = System.currentTimeMillis();
		System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
	}

	@Async
	public void doTaskThree() {
		System.out.println("开始做任务三");
		long start = System.currentTimeMillis();
		try {
			Thread.sleep(random.nextInt(10000));
		}catch(InterruptedException ex){
			System.out.println("错误3");
		}
		long end = System.currentTimeMillis();
		System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
	}

}

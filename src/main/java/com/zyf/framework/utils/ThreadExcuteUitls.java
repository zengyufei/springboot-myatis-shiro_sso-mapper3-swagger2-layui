package com.zyf.framework.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zengyufei on 2016/11/25.
 */
public class ThreadExcuteUitls {

	private final static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
	private final static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	private ThreadExcuteUitls(){}

	public static void fixeThread(Runnable runnable){
		fixedThreadPool.execute(runnable);
	}

	public static void cacheThread(Runnable runnable){
		cachedThreadPool.execute(runnable);
	}

	public static void main(String[] args) {
		testCacheThread();
		testFixeThread();
	}

	private static void testFixeThread() {
		//线程池队列大小
		System.out.println(Runtime.getRuntime().availableProcessors());
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		for (int i = 0; i < 20; i++) {
			final int index = i;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//JDK1.8 Lambda
			executorService.execute(()->{
				System.out.println(Thread.currentThread().getName() + " = " + index);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
	}

	private static void testCacheThread() {
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			final int index = i;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//JDK1.8 Lambda
			executorService.execute(()->{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName());
			});
		}
	}
}

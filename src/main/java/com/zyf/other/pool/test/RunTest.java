package com.zyf.other.pool.test;

import com.zyf.other.pool.array.ArrayBlockingPool;
import com.zyf.other.pool.test.factory.PoolEntityObjectFactory;

public class RunTest {

	public static void main(String[] args) {
		int size = 2;//起步创建2个池对象常存
		PoolEntityObjectFactory objectFactory = new PoolEntityObjectFactory();//对象生产工厂
		ArrayBlockingPool arrayBlockingPool = new ArrayBlockingPool(objectFactory, size);//创建出一个池子里面有2个对象
		for (int i = 0; i < 8; i++) {//遍历8次，看是否会重复利用这2个对象；如果创建出第3个对象，则出错误了，但没有这种情况
			Object o = arrayBlockingPool.get();//从池子取出一个对象
			System.out.println(o);//业务输出
			arrayBlockingPool.release(o);//回收池对象
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

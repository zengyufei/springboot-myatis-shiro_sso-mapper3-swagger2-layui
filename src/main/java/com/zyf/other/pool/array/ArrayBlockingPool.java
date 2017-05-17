package com.zyf.other.pool.array;

import com.zyf.other.pool.BlockingPool;
import com.zyf.other.pool.ObjectFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by zengyufei on 2016/11/25.
 */
public class ArrayBlockingPool<T> implements BlockingPool<T> {

	private BlockingQueue<T> blockingQueue;
	private List<T> list;
	private Integer size;
	private ObjectFactory<T> objectFactory;

	public ArrayBlockingPool(ObjectFactory objectFactory, int size) {
		list = new ArrayList<>(size);
		this.size = size;
		this.objectFactory = objectFactory;
		blockingQueue = new ArrayBlockingQueue<>(size);
		init();
	}

	public void init() {
		for (int i = 0; i < size; i++) {
			T t = objectFactory.create();
			list.add(t);
			try {
				blockingQueue.put(t);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void valid() {

	}

	@Override
	public void release(T t) {
		try {
			blockingQueue.put(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void releaseAll() {
		for (T t : list) {
			try {
				if(!blockingQueue.contains(t))
					blockingQueue.put(t);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public T get() {
		try {
			return blockingQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public T get(long time, TimeUnit timeUnit) {
		try {
			return blockingQueue.poll(time, timeUnit);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}



}

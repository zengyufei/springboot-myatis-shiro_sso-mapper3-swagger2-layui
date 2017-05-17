package com.zyf.framework.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class GuavaCache {

	private int maxSize = 10000;
	private long accessExpireTime = 60;
	private long writeExpireTime = 60;
	private TimeUnit timeUnit = TimeUnit.MINUTES;

	private Cache<String, Object> memberCache;

	public Cache<String, Object> callableCached() {
		if (memberCache == null) {
			memberCache = CacheBuilder.newBuilder().maximumSize(getMaxSize()).
					expireAfterWrite(getWriteExpireTime(), getTimeUnit())
					.expireAfterAccess(getAccessExpireTime(), getTimeUnit()).build();
		}
		return memberCache;
	}
	
	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public long getAccessExpireTime() {
		return accessExpireTime;
	}

	public void setAccessExpireTime(long accessExpireTime) {
		this.accessExpireTime = accessExpireTime;
	}

	public long getWriteExpireTime() {
		return writeExpireTime;
	}

	public void setWriteExpireTime(long writeExpireTime) {
		this.writeExpireTime = writeExpireTime;
	}

}

package com.zyf.framework.utils;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhCacheUtils {
	private static Cache cache;

	private EhCacheUtils(){}

	public static Cache getCache() {
		if(cache == null)
			return cache = new CacheManager().getCache("testCache");
		else
			return cache;
	}

	/*
 * 通过名称从缓存中获取数据
 */
	public static Object getCacheElement(Object cacheKey) {
		Element e = getCache().get(cacheKey);
		if (e == null) {
			return null;
		}
		return e.getValue();
	}

	/*
	 * 将对象添加到缓存中
	 */
	public static void addToCache(Object cacheKey, Object result) {
		Element element = new Element(cacheKey, result);
		getCache().put(element);
	}


}

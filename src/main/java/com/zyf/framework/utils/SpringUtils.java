package com.zyf.framework.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring 工具类
 */
@Component
public class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext context;

	private SpringUtils() {
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringUtils.context = context;
	}

	public static <T> T getBean(Class<T> clazz) {
		if (clazz == null) return null;
		return context.getBean(clazz);
	}

	public static ApplicationContext getContext() {
		if (context == null) return null;
		return context;
	}

}

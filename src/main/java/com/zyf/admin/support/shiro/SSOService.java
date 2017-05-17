package com.zyf.admin.support.shiro;

import com.zyf.admin.support.shiro.entity.SSOConfig;

import java.io.FileNotFoundException;

/**
 * sso 主类
 */
public interface SSOService {

	/**
	 * 加载配置文件
	 */
	void loadProperties() throws FileNotFoundException;

	void init();

	/**
	 * 全局配置存储
	 */
	SSOConfig getConfig();
}

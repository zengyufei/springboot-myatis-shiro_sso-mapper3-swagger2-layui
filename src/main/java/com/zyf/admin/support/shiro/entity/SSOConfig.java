package com.zyf.admin.support.shiro.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @className:
 * @classDesc:
 * @author：zengyufei
 * @createTime: 2017年04月25日 10:44
 * www.zzsim.com
 */
@Data
public class SSOConfig {

	String key;
	String cookiesName;
	String encrypt;
	String domain;
	String path;
	boolean checkIp;
	boolean checkBrowser;
	int maxAge;

	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private static SSOConfig defaultConfig;

	public void setDefaultConfig(SSOConfig defaultConfig) {
		this.defaultConfig = defaultConfig;
	}

	public static SSOConfig get(){
		return defaultConfig;
	}
}

package com.zyf.admin.support.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * spring mvc 基本设置
 */
@ConfigurationProperties(prefix = SpringMvcProperties.SPRINGMVC_PREFIX)
public class SpringMvcProperties {

	public final static String SPRINGMVC_PREFIX = "springMvc";

	private String staticViewName;

	private String resourceUriAndPath;

	public String getStaticViewName() {
		return staticViewName;
	}

	public void setStaticViewName(String staticViewName) {
		this.staticViewName = staticViewName;
	}

	public String getResourceUriAndPath() {
		return resourceUriAndPath;
	}

	public void setResourceUriAndPath(String resourceUriAndPath) {
		this.resourceUriAndPath = resourceUriAndPath;
	}
}

package com.zyf.framework.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = MapperRefreshProperties.MYBATIS_PREFIX)
public class MapperRefreshProperties {

	public static final String MYBATIS_PREFIX = "mapper";

	private boolean mapperRefreshEnable = false;

	private String mapperRefreshPath;
	private String mappingPath;

	private int delaySeconds;
	private int sleepSeconds;

	public static String getMybatisPrefix() {
		return MYBATIS_PREFIX;
	}

	public boolean isMapperRefreshEnable() {
		return mapperRefreshEnable;
	}

	public void setMapperRefreshEnable(boolean mapperRefreshEnable) {
		this.mapperRefreshEnable = mapperRefreshEnable;
	}

	public String getMapperRefreshPath() {
		return mapperRefreshPath;
	}

	public void setMapperRefreshPath(String mapperRefreshPath) {
		this.mapperRefreshPath = mapperRefreshPath;
	}

	public String getMappingPath() {
		return mappingPath;
	}

	public void setMappingPath(String mappingPath) {
		this.mappingPath = mappingPath;
	}

	public int getDelaySeconds() {
		return delaySeconds;
	}

	public void setDelaySeconds(int delaySeconds) {
		this.delaySeconds = delaySeconds;
	}

	public int getSleepSeconds() {
		return sleepSeconds;
	}

	public void setSleepSeconds(int sleepSeconds) {
		this.sleepSeconds = sleepSeconds;
	}
}

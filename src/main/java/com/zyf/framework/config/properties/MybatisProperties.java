package com.zyf.framework.config.properties;

import org.apache.ibatis.session.ExecutorType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Configuration properties for Mybatis.
 *
 * @author Eddú Meléndez
 */
@ConfigurationProperties(prefix = MybatisProperties.MYBATIS_PREFIX)
public class MybatisProperties {

	public static final String MYBATIS_PREFIX = "mybatis";

	/**
	 * Config file path.
	 */
	private String configLocation;

	/**
	 * Location of mybatis dao files.
	 */
	private String mapperLocations;

	/**
	 * Package to scan domain objects.
	 */
	private String typeAliasesPackage;

	/**
	 * Package to scan handlers.
	 */
	private String typeHandlersPackage;

	/**
	 * Package to scan TypeHandler.
	 */
	private String[] typeHandlers;

	private Map<String, String> typeScanMap;

	/**
	 * Check the support file exists.
	 */
	private boolean checkConfigLocation = false;

	/**
	 * Execution mode.
	 */
	private ExecutorType executorType = ExecutorType.SIMPLE;

	private boolean mapperRefreshEnable = false;

	private String mapperRefreshPath;

	private String mappersInterfacePackage;

	public String getMapperLocations() {
		return this.mapperLocations;
	}

	public void setMapperLocations(String mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

	public String getTypeHandlersPackage() {
		return this.typeHandlersPackage;
	}

	public void setTypeHandlersPackage(String typeHandlersPackage) {
		this.typeHandlersPackage = typeHandlersPackage;
	}

	public String getTypeAliasesPackage() {
		return this.typeAliasesPackage;
	}

	public void setTypeAliasesPackage(String typeAliasesPackage) {
		this.typeAliasesPackage = typeAliasesPackage;
	}

	public boolean isCheckConfigLocation() {
		return this.checkConfigLocation;
	}

	public void setCheckConfigLocation(boolean checkConfigLocation) {
		this.checkConfigLocation = checkConfigLocation;
	}

	public ExecutorType getExecutorType() {
		return this.executorType;
	}

	public void setExecutorType(ExecutorType executorType) {
		this.executorType = executorType;
	}

	public String getConfigLocation() {
		return configLocation;
	}

	public void setConfigLocation(String configLocation) {
		this.configLocation = configLocation;
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

	public String[] getTypeHandlers() {
		return typeHandlers;
	}

	public void setTypeHandlers(String[] typeHandlers) {
		this.typeHandlers = typeHandlers;
	}

	public Map<String, String> getTypeScanMap() {
		return typeScanMap;
	}

	public void setTypeScanMap(Map<String, String> typeScanMap) {
		this.typeScanMap = typeScanMap;
	}

	public String getMappersInterfacePackage() {
		return mappersInterfacePackage;
	}

	public void setMappersInterfacePackage(String mappersInterfacePackage) {
		this.mappersInterfacePackage = mappersInterfacePackage;
	}
}

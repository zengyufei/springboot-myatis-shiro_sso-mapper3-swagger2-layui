package com.zyf.admin.support.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = ShiroProperties.SHIRO_PREFIX, locations = "classpath:/config/shiroConfig.properties")
public class ShiroProperties {

	public final static String SHIRO_PREFIX = "shiro";

	private boolean enableShrio;
	private String urlPatterns;
	private String loginUrl;
	private String successUrl;
	private String unauthorizedUrl;
	private String redirectUrl;//logout redirect page
	private String filterChainDefinitions;

	private String sessionIdCookieConstructorArg;
	private String sessionIdCookieName;
	private boolean sessionIdCookieHttpOnly;
	private int sessionIdCookieMaxAge;

	private String rememberMeConstructorArg;
	private boolean rememberMeHttpOnly;
	private String rememberMeName;
	private int rememberMeMaxAge;
	private String rememberMeCipherKey;

	private boolean sessionIdCookieEnabled;
	private int globalSessionTimeout;

	private String cacheManagerConfigFile;

	public String getCacheManagerConfigFile() {
		return cacheManagerConfigFile;
	}

	public void setCacheManagerConfigFile(String cacheManagerConfigFile) {
		this.cacheManagerConfigFile = cacheManagerConfigFile;
	}

	public boolean isEnableShrio() {
		return enableShrio;
	}

	public void setEnableShrio(boolean enableShrio) {
		this.enableShrio = enableShrio;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getFilterChainDefinitions() {
		return filterChainDefinitions;
	}

	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	public boolean isRememberMeHttpOnly() {
		return rememberMeHttpOnly;
	}

	public void setRememberMeHttpOnly(boolean rememberMeHttpOnly) {
		this.rememberMeHttpOnly = rememberMeHttpOnly;
	}

	public String getRememberMeName() {
		return rememberMeName;
	}

	public void setRememberMeName(String rememberMeName) {
		this.rememberMeName = rememberMeName;
	}

	public int getRememberMeMaxAge() {
		return rememberMeMaxAge;
	}

	public void setRememberMeMaxAge(int rememberMeMaxAge) {
		this.rememberMeMaxAge = rememberMeMaxAge;
	}

	public String getRememberMeCipherKey() {
		return rememberMeCipherKey;
	}

	public void setRememberMeCipherKey(String rememberMeCipherKey) {
		this.rememberMeCipherKey = rememberMeCipherKey;
	}

	public boolean isSessionIdCookieEnabled() {
		return sessionIdCookieEnabled;
	}

	public void setSessionIdCookieEnabled(boolean sessionIdCookieEnabled) {
		this.sessionIdCookieEnabled = sessionIdCookieEnabled;
	}

	public int getGlobalSessionTimeout() {
		return globalSessionTimeout;
	}

	public void setGlobalSessionTimeout(int globalSessionTimeout) {
		this.globalSessionTimeout = globalSessionTimeout;
	}

	public String getSessionIdCookieConstructorArg() {
		return sessionIdCookieConstructorArg;
	}

	public void setSessionIdCookieConstructorArg(String sessionIdCookieConstructorArg) {
		this.sessionIdCookieConstructorArg = sessionIdCookieConstructorArg;
	}

	public String getSessionIdCookieName() {
		return sessionIdCookieName;
	}

	public void setSessionIdCookieName(String sessionIdCookieName) {
		this.sessionIdCookieName = sessionIdCookieName;
	}

	public boolean isSessionIdCookieHttpOnly() {
		return sessionIdCookieHttpOnly;
	}

	public void setSessionIdCookieHttpOnly(boolean sessionIdCookieHttpOnly) {
		this.sessionIdCookieHttpOnly = sessionIdCookieHttpOnly;
	}

	public int getSessionIdCookieMaxAge() {
		return sessionIdCookieMaxAge;
	}

	public void setSessionIdCookieMaxAge(int sessionIdCookieMaxAge) {
		this.sessionIdCookieMaxAge = sessionIdCookieMaxAge;
	}

	public String getRememberMeConstructorArg() {
		return rememberMeConstructorArg;
	}

	public void setRememberMeConstructorArg(String rememberMeConstructorArg) {
		this.rememberMeConstructorArg = rememberMeConstructorArg;
	}

	public String getUrlPatterns() {
		return urlPatterns;
	}

	public void setUrlPatterns(String urlPatterns) {
		this.urlPatterns = urlPatterns;
	}

}

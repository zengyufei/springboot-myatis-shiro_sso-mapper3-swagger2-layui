package com.zyf.admin.support.config;

import com.zyf.admin.support.config.properties.ShiroProperties;
import com.zyf.admin.support.filter.AuthcedFilter;
import com.zyf.admin.support.filter.StatelessAuthcFilter;
import com.zyf.admin.support.shiro.StatelessDefaultSubjectFactory;
import com.zyf.admin.support.shiro.realm.UserRealm;
import com.zyf.commons.service.sys.UserService;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import java.util.*;

@Configuration
@EnableConfigurationProperties(value = ShiroProperties.class)
public class ShiroConfig {

	/*
		需要注入 bean 则需要在当前类上注解 @Configuration
	 */
	@Autowired
	private ShiroProperties shiroProperties;
	@Autowired
	private UserService userService;

	/**
	 * 重新设置 shiro 过滤器配置
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
		filterRegistration.setEnabled(true);
		filterRegistration.addUrlPatterns(this.shiroProperties.getUrlPatterns());
		filterRegistration.setOrder(1);
		List<String> urls = new ArrayList<>(1);
		urls.add("/*");
		filterRegistration.setUrlPatterns(urls);
		filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
		return filterRegistration;
	}

	/**
	 * 定义 shiroFilter 过滤器
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(getSecurityManager());
		bean.setLoginUrl(this.shiroProperties.getLoginUrl());
		bean.setSuccessUrl(this.shiroProperties.getSuccessUrl());
		bean.setUnauthorizedUrl(this.shiroProperties.getUnauthorizedUrl());

		Map<String, Filter> filters = new HashMap<>();
		filters.put("statelessAuthc", getStatelessAuthcFilter(userService));
		//filters.put("authced", getAuthcedFilter());
		filters.put("anon", new AnonymousFilter());
		bean.setFilters(filters);

		if (this.shiroProperties.isEnableShrio()) {
			bean.setFilterChainDefinitions(this.shiroProperties.getFilterChainDefinitions());
		}
		return bean;
	}

	@Bean("securityManager")
	public DefaultWebSecurityManager getSecurityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(getUserRealm());
		securityManager.setSubjectFactory(getSubjectFactory());
		//securityManager.setCacheManager(getCacheManager());
		securityManager.setSessionManager(getSessionManager());
		SessionStorageEvaluator sessionStorageEvaluator = ((DefaultSubjectDAO) securityManager.getSubjectDAO()).getSessionStorageEvaluator();
		DefaultSessionStorageEvaluator sse = (DefaultSessionStorageEvaluator) sessionStorageEvaluator;
		sse.setSessionStorageEnabled(false);
		return securityManager;
	}

	@Bean("sessionManager")
	public DefaultSessionManager getSessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionIdCookieEnabled(false);
		//sessionManager.setCacheManager(getCacheManager());
		sessionManager.setSessionValidationSchedulerEnabled(false);
		return sessionManager;
	}

	@Bean
	public UserRealm getUserRealm() {
		UserRealm userRealm = new UserRealm();
		//userRealm.setCacheManager(cacheManager());
		return userRealm;
	}

	/**
	 *  开启shiro aop注解支持.
	 *  使用代理方式;所以需要开启代码支持;
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(getSecurityManager());
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	public StatelessDefaultSubjectFactory getSubjectFactory() {
		return new StatelessDefaultSubjectFactory();
	}

	/*@Bean
	public EhCacheManager cacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile(this.shiroProperties.getCacheManagerConfigFile());
		return cacheManager;
	}*/

	/**
	 * url 拦截器处理
	 */
	private StatelessAuthcFilter getStatelessAuthcFilter(UserService userService) {
		StatelessAuthcFilter statelessAuthcFilter = new StatelessAuthcFilter();
		statelessAuthcFilter.setUserService(userService);
		statelessAuthcFilter.setCookiesOutTime(shiroProperties.getGlobalSessionTimeout());
		return statelessAuthcFilter;
	}

	/**
	 * 已登录访问 login 自动跳转
	 */
	private AuthcedFilter getAuthcedFilter() {
		AuthcedFilter authcedFilter = new AuthcedFilter();
		return authcedFilter;
	}

	/**
	 * 与 springmvc 整合，捕获异常重定向跳转
	 */
	@Bean
	public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver(){
		SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
		Properties properties = new Properties();
		properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "redirect:" + this.shiroProperties.getUnauthorizedUrl());
		properties.setProperty("org.apache.shiro.authz.UnauthenticatedException", "redirect:" + this.shiroProperties.getLoginUrl());
		simpleMappingExceptionResolver.setExceptionMappings(properties);
		return simpleMappingExceptionResolver;
	}
}
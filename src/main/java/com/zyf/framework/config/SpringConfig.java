package com.zyf.framework.config;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration //声明是一个 springboot 配置文件
@ComponentScan(basePackages = {"com.zyf.admin.server.service"}) //显性扫描
public class SpringConfig {

	private final static String EHCACHE_CONFIGURATION = "classpath:/static/ehcache/testCache.xml";

	@Bean
	public EhCacheManagerFactoryBean getEhCacheManagerFactoryBean(){
		EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource resource = resourcePatternResolver.getResource(EHCACHE_CONFIGURATION);
		ehCacheManagerFactoryBean.setConfigLocation(resource);
		return ehCacheManagerFactoryBean;
	}

	/**
	 * 创建缓存
	 * @return
	 */
	@Bean("ehCacheCacheManager")
	public EhCacheCacheManager getEhCacheCacheManager(){
		EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
		ehCacheCacheManager.setCacheManager(getEhCacheManagerFactoryBean().getObject());
		return ehCacheCacheManager;
	}

}

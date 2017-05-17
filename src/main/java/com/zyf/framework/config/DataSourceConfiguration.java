package com.zyf.framework.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

/**
 * 数据库配置
 */
@Configuration
public class DataSourceConfiguration {

	// Druid连接池
	// 连接池配置：https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8
	private DruidDataSource pool;

	@Bean(name = "dataSource", destroyMethod = "close") // 指向方法名
	@ConfigurationProperties(prefix = "spring.datasource") // 读取 springboot 配置文件所有以 spring.datasource 开头的配置
	public DataSource dataSource() {
		if (pool == null)
			pool = new DruidDataSource();
		return pool;
	}

	@PreDestroy // 与 xml 声明 bean 的属性 destroy-Method 一样，换成注解的方式
	public void close() {
		if (this.pool != null) {
			this.pool.close();
		}
	}

}

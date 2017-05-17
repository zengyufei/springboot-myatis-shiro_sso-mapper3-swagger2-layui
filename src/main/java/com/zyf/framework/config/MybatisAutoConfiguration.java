package com.zyf.framework.config;

import com.github.pagehelper.PageInterceptor;
import com.zyf.framework.config.properties.MapperRefreshProperties;
import com.zyf.framework.config.properties.MybatisProperties;
import com.zyf.framework.plugin.MapperRefresh;
import com.zyf.framework.plugin.PerformanceInterceptor;
import com.zyf.framework.plugin.SqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Properties;

/*
声明这是一个 springboot 配置类
 */
@Configuration
/*
当 SqlSessionFactory.class, org.mybatis.spring.SqlSessionFactoryBean.class 类被加载之后，才实例化当前对象
 */
@ConditionalOnClass({SqlSessionFactory.class, org.mybatis.spring.SqlSessionFactoryBean.class})
@AutoConfigureAfter(DataSourceAutoConfiguration.class) //类的载入顺序，在。。。之后
@EnableConfigurationProperties({MybatisProperties.class, MapperRefreshProperties.class})
@EnableTransactionManagement
public class MybatisAutoConfiguration implements TransactionManagementConfigurer {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private MybatisProperties properties;
	@Autowired
	private MapperRefreshProperties mapperRefreshProperties;

	@Autowired
	DataSource dataSource;

	@Autowired(required = false)
	private Interceptor[] interceptors;

	@Autowired
	private ResourceLoader resourceLoader = new DefaultResourceLoader();

	@PostConstruct // 在构造时调用该方法
	public void checkConfigFileExists() {
		if (this.properties.isCheckConfigLocation()) {
			Resource resource = this.resourceLoader
					.getResource(this.properties.getConfigLocation());
			Assert.state(resource.exists(),
					"Cannot find support location: " + resource
							+ " (please add support file or check your Mybatis "
							+ "configuration)");
		}
	}

	@Bean(name = "sqlSessionFactory")
	@ConditionalOnMissingBean //判断是否执行初始化代码，即如果用户已经创建了该类，则相关的初始化代码不再执行
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		//注册 typeHandlers
		registerTypeHandlers(factory);
		//注册dataSource
		factory.setDataSource(dataSource);
		if (StringUtils.hasText(this.properties.getConfigLocation())) {
			factory.setConfigLocation(
					this.resourceLoader.getResource(this.properties.getConfigLocation()));
		} else {
			if (this.interceptors != null && this.interceptors.length > 0) {
				factory.setPlugins(this.interceptors);
			}
			//实体类别名注册，用于 mapper.xml resultMap type
			factory.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			//扫描 mapper.xml
			factory.setMapperLocations(resolver.getResources(this.properties.getMapperLocations()));
			//热刷新 mapper.xml 插件注册
			MapperRefresh mapperRefresh = getMapperRefresh();
			factory.setMapperRefresh(mapperRefresh);
		}
		return factory.getObject();
	}

	/*
		idea 更改 xml 文件后，需要 ctrl+shift+F9 让 IDE 拷贝到部署目录才能生效
	 */
	private MapperRefresh getMapperRefresh() {
		MapperRefresh mapperRefresh = new MapperRefresh();
		mapperRefresh.setEnabled(this.mapperRefreshProperties.isMapperRefreshEnable());
		mapperRefresh.setMappingPath(this.mapperRefreshProperties.getMapperRefreshPath());
		mapperRefresh.setDelaySeconds(this.mapperRefreshProperties.getDelaySeconds());
		mapperRefresh.setSleepSeconds(this.mapperRefreshProperties.getSleepSeconds());
		return mapperRefresh;
	}

	private void registerTypeHandlers(SqlSessionFactoryBean factory)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String[] xmlTypeHandlers = properties.getTypeHandlers();
		TypeHandler<?>[] typeHandlers = new TypeHandler<?>[xmlTypeHandlers.length];
		for (int i = 0; i < typeHandlers.length; i++) {
			Class<?> aClass = Class.forName(xmlTypeHandlers[i]);
			Object o = aClass.newInstance();
			typeHandlers[i] = (TypeHandler<?>) o;
		}
		factory.setTypeHandlers(typeHandlers);
		factory.setTypeScanMap(properties.getTypeScanMap());
	}

	/**
	 * 用于实际查询的sql工具,传统dao开发形式可以使用这个,基于mapper代理则不需要注入
	 */
	@Bean
	@ConditionalOnMissingBean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory,
				this.properties.getExecutorType());
	}

	/**
	 * 分页插件
	 * 分页参数说明：https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md
	 * @return PageHelper
	 */
	@Bean
	public PageInterceptor pageHelper() {
		log.info("注册MyBatis分页插件PageHelper");
		PageInterceptor pageHelper = new PageInterceptor();
		Properties p = new Properties();
		p.setProperty("helperDialect", "mysql");
		p.setProperty("offsetAsPageNum", "true");
		p.setProperty("rowBoundsWithCount", "true");
		p.setProperty("reasonable", "true");
		pageHelper.setProperties(p);
		return pageHelper;
	}

	/**
	 * 性能显示插件打印SQL
	 */
	@Bean
	public PerformanceInterceptor performanceInterceptor() {
		log.info("注册性能显示插件PerformanceInterceptor");
		return new PerformanceInterceptor();
	}

	/**
	 * 事务管理,具体使用在service层加入@Transactional注解
	 */
	@Bean(name = "transactionManager")
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
	/**
	 * 事务模板
	 */
	@Bean(name = "transactionTemplate")
	public TransactionTemplate getTransactionTemplate(PlatformTransactionManager transactionManager) {
		return new TransactionTemplate(transactionManager);
	}
}


package com.zyf;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	/**
	 * sso 单点登录配置文件加载。
	 * 必须
	 */
	/*@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.addListener(KissoConfigListener.class);
		servletContext.setInitParameter(WebKissoConfigurer.CONFIG_LOCATION_PARAM, "classpath:config/sso.properties");
		super.onStartup(servletContext);
	}
*/
	/**
	 * 如果采用 jetty 来启动：
	 * 1 需要 org.springframework.boot：spring-boot-starter-web
	    排除org.springframework.boot：spring-boot-starter-tomcat jar包.
	 * 2 打开 Bean com.zyf.ServletInitializer.servletContainer 方法
	 * 3 引入 org.springframework.boot：spring-boot-starter-jetty jar包
	 */
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory();
		return factory;
	}
}

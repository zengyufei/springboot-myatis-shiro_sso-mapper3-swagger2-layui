package com.zyf.admin.support.config;

import com.zyf.admin.support.config.factory.StringToLocalDateConverter;
import com.zyf.admin.support.config.factory.StringToLocalDateTimeConverter;
import com.zyf.admin.support.config.properties.SpringMvcProperties;
import com.zyf.admin.support.shiro.SSOService;
import com.zyf.admin.support.shiro.impl.SSOServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.MultipartConfigElement;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"com.zyf.admin.web"})
@EnableConfigurationProperties(value = SpringMvcProperties.class)
public class WebConfiguration extends WebMvcConfigurerAdapter {

	private static final Charset UTF8 = Charset.forName("UTF-8");

	@Autowired
	private SpringMvcProperties springMvcProperties;
	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebConfiguration.class);
	}

	@PostConstruct
	public void addConversionConfig() {
		ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer)handlerAdapter.getWebBindingInitializer();
		if (initializer.getConversionService() != null) {
			GenericConversionService genericConversionService = (GenericConversionService)initializer.getConversionService();
			genericConversionService.addConverter(new StringToLocalDateConverter());
			genericConversionService.addConverter(new StringToLocalDateTimeConverter());
		}
	}

	/**
	 * 消息转换器，如果使用 msg 返回则用不上
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		/*converters.add(mappingJackson2HttpMessageConverter());
		converters.add(stringHttpMessageConverter());*/
	}

	/**
	 * Json 消息转换器暂时不启用
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
		List<MediaType> supportedMediaTypes = new ArrayList<>(5);
		supportedMediaTypes.add(new MediaType("text", "html", UTF8));
		supportedMediaTypes.add(new MediaType("text", "xml", UTF8));
		supportedMediaTypes.add(new MediaType("text", "plain", UTF8));
		supportedMediaTypes.add(new MediaType("text", "json", UTF8));
		supportedMediaTypes.add(new MediaType("application", "json", UTF8));
		jsonMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		return jsonMessageConverter;
	}

	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(UTF8);
		List<MediaType> supportedMediaTypes = new ArrayList<>(5);
		supportedMediaTypes.add(new MediaType("text", "html", UTF8));
		supportedMediaTypes.add(new MediaType("text", "xml", UTF8));
		supportedMediaTypes.add(new MediaType("text", "plain", UTF8));
		supportedMediaTypes.add(new MediaType("text", "json", UTF8));
		supportedMediaTypes.add(new MediaType("application", "json", UTF8));
		stringHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
		return stringHttpMessageConverter;
	}

	/**
	 * 上传下载配置
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("50MB");
        factory.setMaxRequestSize("50MB");
        return factory.createMultipartConfig();
    }

	/**
	 * 直接访问jsp的路由
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		if (StringUtils.isNotBlank(this.springMvcProperties.getStaticViewName())) {
			String[] urls = this.springMvcProperties.getStaticViewName().split(";");
			for (String url : urls) {
				String[] viewName = url.split("=");
				registry.addViewController(viewName[0]).setViewName(viewName[1]);
			}
		}
		super.addViewControllers(registry);
	}

	@Bean
	public SSOService getSSOService(){
		SSOServiceImpl ssoService = new SSOServiceImpl();
		ssoService.setProperties("classpath:config/sso.properties");
		ssoService.loadProperties();
		return ssoService;
	}

	/**
	 * 配置拦截器
	 */
	/*@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(new TestInterceptors()).excludePathPatterns("/static*//**").addPathPatterns("*//**");
	 registry.addInterceptor(shiroInterceptors())
	 //.excludePathPatterns("/static*//**")
	 .addPathPatterns("*/


}
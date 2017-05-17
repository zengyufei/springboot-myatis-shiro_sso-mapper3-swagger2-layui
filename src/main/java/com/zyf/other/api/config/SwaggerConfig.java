package com.zyf.other.api.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalDateTime;

/**
 * API 管理，访问地址： /swagger-ui.html
 * 访问 /api 重定向到 /swagger-ui.html
 */
//@Configuration
//@EnableSwagger2
public class SwaggerConfig {

	public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.zyf";
	public static final String VERSION = "1.0.0";

	ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Swagger API")
				.description("This is to show api description")
				.license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.termsOfServiceUrl("")
				.version(VERSION)
				.build();
	}

	@Bean
	public Docket customImplementation(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
				.build()
				.directModelSubstitute(LocalDateTime.class, java.sql.Date.class)
				.apiInfo(apiInfo());
	}
}

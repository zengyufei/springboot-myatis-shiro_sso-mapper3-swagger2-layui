package com.zyf;

import com.zyf.framework.config.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


//@EnableAsync    //开启异步处理
//@EnableScheduling   //开启定时任务
@EnableAutoConfiguration(exclude = MybatisAutoConfiguration.class)
@ComponentScan
@SpringBootApplication
public class Application {

	/**
	 * 当使用 tomcat 作为 springboot 容器时，该方法才可以直接使用来启动 springboot
	 * 如果使用 jetty 作为 springboot 容器时，该方法会报错，要使用 mvn spring-boot:run 启动
	 * 端口修改在配置文件 application-xxx.properties 中设置
	 * 如果 idea 启动失败，请设置 debug -> edit Configurations -> Working directory -> $MODULE_DIR$
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

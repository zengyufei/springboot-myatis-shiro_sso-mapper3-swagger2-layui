Springboot 测验DEMO
=====

该框架是接触 Springboot 练手做成的，主要是用来练习基本功。

### 技术选型：

###### 后端支持：

主体支持： **JDK1.8**、**Springboot1.4.2**

页面支持：**JSP/thymeleaf**、**jstl**

ORM支持: **mybatis3.2.8**

ORM插件支持: **mapper3.3**、**PageHelp4.1** 分页

权限支持：**shiro1.2.5**

访问接口支持: **swagger2**

JSON支持:**fastjson**

连接池支持：**druid**

mapper xml生成支持：**mybatis-generator-core 1.3.5**

时间操作支持：**joda-time**

缓存支持：**ehcache** 、**guava**

参数验证支持：**jsr303**

web容器支持：**内嵌tomcat**

maven支持：**maven3.3.9**

maven仓库支持：**阿里云**

热部署支持：**springloaded**

日志支持：**log4j**

消息队列支持：**rabbitmq**

###### 前端支持：

模块化支持：**requireJS**

主体支持：**jQuery3.1**

风格支持：**bootstrap**

模板支持：**artTemplate**

表格支持：**datatables**

图标支持：**font-awesome**

弹窗支持：**layer**

websocket支持：**sockjs**

树结构支持：**ztree**

### 支持情况

1. 访问`http://localhost:8081/api`可以查看项目的接口情况。
2. tomcat 端口在config/application-test.properties更改，默认8081。
3. 默认登录admin/admin
4. 热刷新实体 mapper.xml文件 MapperRefresh.java（用不上）
5. 对象池例子 RunTest#main
6. 时间处理类 com.zyf.framework.utils.DateJodaTimeUtils
7. 下载工具类 com.zyf.framework.utils.DownLoadUtils
8. 缓存工具类 com.zyf.framework.utils.GuavaCache、com.zyf.framework.utils.EhCacheUtils
9. 资源路径工具类 com.zyf.framework.utils.PathUtils
10. thymeleaf 模板 需要在配置文件打开，且打开后只使用thymeleaf，jsp失效
11. 默认运行定时任务；关闭请注释 com.zyf.Application 注解
12. 生成 mapper xml 需要修改 static/mybatis/generatorConfig.xml 配置，修改好后运行 mvn mybatis-generator:generate
13. 切换配置文件 application.properties 设置参数
14. 自动创建数据库，关闭请在配置文件找到JDBC链接删除 &createDatabaseIfNotExist=true
15. 自动运行SQL脚本，关闭注释掉 spring.datasource.schema
16. 打印后台语句 PerformanceInterceptor.java


## 框架发展

### DEMO小样发展阶段一

1. springboot 研究
2. 测试 springboot各版本区别
3. main 方式运行 springboot
4. 引入 jsp，并测试
5. 引入 springloaded，并测试
5. 测试 thymeleaf
6. 引入 mybatis，并测试
7. 引入mybatis-generator-core 1.3.5，并测试
8. 引入log4j
9. 引入mapper3.3，并测试
10. 引入PageHelp4.1，并测试
11. 引入shiro，并测试
12. 引入swagger2，并测试
13. 引入ehcache，并测试
14. 引入websocket，并测试
15. 引入jsr303，并测试
16. 打开Async异步操作，并测试
17. 引入rabbitmq，并测试
18. 打开定时任务，并测试
19. 成型，开始引入前端的框架


### DEMO小样发展阶段二

1. 引入 bootstrap
2. 找到一个 bootstrap模板套用
3. 引入 requireJS，并测试
4. 引入layer，并测试
5. 引入sockjs，并测试
6. 引入datatables，并测试
7. 引入artTemplate，并测试


### DEMO小样发展阶段三

1. 开始制作页面
2. 找到GitHub登录页面模板，套用
3. 套用主页
4. 制作左侧菜单
5. 制作用户管理
6. 制作角色管理
7. 制作前端工具类
8. 制作资源管理

### 作者
@at曾玉飞

### 邮箱
zengyufei@163.com

## 更多信息
https://github.com/zengyufei/springboot-mybatis-mapper-plugin-bootstrap-requirejs

### 博客
http://www.cnblogs.com/zengyufei/

PS:
关闭Swagger2(/api)能加速项目启动，打开关闭注解;路径 com.zyf.other.api.config.SwaggerConfig

关闭Websocket能加速项目启动，打开关闭注解;路径 com.zyf.other.websocket.config.MessageWebSocketConfig

关闭定时任务能加速项目启动，打开关闭注解;路径 com.zyf.Application

关闭异步请求能加速项目启动，打开关闭注解;路径 com.zyf.Application


关闭项目启动运行脚本能加速项目启动，打开关闭 [spring.rabbitmq.listener.auto-startupspring.datasource.schema，spring.datasource.continueOnError]

关闭Rabbitmq消息队列，否则启动报错；配置项 spring.rabbitmq.listener.auto-startup


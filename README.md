Springboot 测验DEMO
=====

该框架是接触 Springboot 练手做成的，主要是用来练习基本功。

### 使用说明

1. 下载解压
2. IDE 安装 lombok，导入 IDE 中（可选）
3. 修改 jdbc 属性 （数据库和脚本自动创建和导入）
4. main 方法执行或 maven 命令 spring-boot:run 执行
5. main 方法执行 idea 工具需要设置 Working Directory 为当前目录。
6. 访问 http://localhost:8082/
7. 账号/密码： admin/admin


![image](https://github.com/zengyufei/springboot-myatis-shiro_sso-mapper3-swagger2-layui/tree/master/tmp/img/login.png)
![image](https://github.com/zengyufei/springboot-myatis-shiro_sso-mapper3-swagger2-layui/tree/master/tmp/img/index.png)
![image](https://github.com/zengyufei/springboot-myatis-shiro_sso-mapper3-swagger2-layui/tree/master/tmp/img/user_manager.png)
![image](https://github.com/zengyufei/springboot-myatis-shiro_sso-mapper3-swagger2-layui/tree/master/tmp/img/user_add.png)
![image](https://github.com/zengyufei/springboot-myatis-shiro_sso-mapper3-swagger2-layui/tree/master/tmp/img/role_manager.png)
![image](https://github.com/zengyufei/springboot-myatis-shiro_sso-mapper3-swagger2-layui/tree/master/tmp/img/role_edit.png)
![image](https://github.com/zengyufei/springboot-myatis-shiro_sso-mapper3-swagger2-layui/tree/master/tmp/img/resource_manager.png)

### 技术选型：

###### 后端支持：

主体支持： **JDK1.8**、**Springboot1.4.2**

页面支持：**html**

ORM支持: **mybatis3.2.8**

ORM插件支持: **mapper3.3**、**PageHelp4.1** 分页

权限支持：**shiro1.2.5**

访问接口支持: **swagger2**

JSON支持:**fastjson**

连接池支持：**druid**

缓存支持：**ehcache** 、**guava**

参数验证支持：**jsr303**

web容器支持：**内嵌tomcat**

maven支持：**maven3.3.9**

maven仓库支持：**阿里云**

代码精简与日志支持： **lombok**

###### 前端支持：

主体支持：**jQuery**

风格支持：**layui**

图标支持：**font-awesome**

弹窗支持：**layer**

websocket支持：**sockjs**

树结构支持：**ztree**

### 支持情况

1. 修改 **config/application-dev.properties** JDBC 链接属性。
2. tomcat 端口在 **config/application-dev.properties** 更改，默认**8082**。
3. 自动创建数据库，关闭请在配置文件找到 JDBC 链接删除 **&createDatabaseIfNotExist=true**。
4. 自动运行SQL脚本，关闭请注释掉 **spring.datasource.schema**。
5. 默认登录 **admin/admin**
6. 打印后台 SQL 语句。 （打开/关闭 注释该方法 com.zyf.framework.config.MybatisAutoConfiguration.pageHelper）
7. 切换配置文件 **application.properties** 设置参数
8. 访问`http://localhost:8082/api`可以查看项目的接口情况。（打开/关闭 com.zyf.other.api.config.SwaggerConfig）。
9. 使用 html，基本使用 ajax 异步请求，页面不刷新。
10. 基于 shiro 改造的 sso 单机实现，登录生成 token 存储在用户 cookies 中，请求解析 cookies，以解析成功作为标识。
11. 交互上使用 layui，使用第三方功能。
12. 热刷新实体 mapper.xml文件 MapperRefresh.java（打开/关闭 config/application-dev.properties -> mapper.mapper-refresh-enable: true/false）
13. 自动注册枚举到 mybatis，查询出来自动转换枚举。
14. 消息转换未配置，如果需要返回 map 自动转换成 UTF-8 json 及需要保证 JDK8 LocalDateTime 类日期的正确性，请打开 com.zyf.admin.support.config.WebConfiguration.configureMessageConverters 注释部分。
15. 采用 mapper3 pageHelper 插件，因此大部分通用 dao 层及 xml 都不比书写。
16. 后台基本使用 resultFull 风格，前端做的事情比较多。
17. 对象池测试 com.zyf.other.pool.test.RunTest#main


## 框架发展

### DEMO小样发展阶段一

1. springboot 研究
2. 测试 springboot 各版本区别
3. main 方式运行 springboot
4. 引入 jsp，并测试
5. 引入 mybatis，并测试
6. 引入 log4j
7. 引入 mapper3.3，并测试
8. 引入 PageHelp4.1，并测试
9. 引入 shiro，并测试
10. 引入 swagger2，并测试
11. 引入 ehcache，并测试
12. 引入 websocket，并测试
13. 引入 jsr303，并测试
14. 打开 Async 异步操作，并测试
15. 引入 rabbitmq，并测试
16. 打开定时任务，并测试
17. 引入 lombok，并测试
18. 建立用户展示层
19. 建立角色展示层
20. 建立资源展示层
21. 引入 layui，开始建设前端页面
22. 建立用户页面
23. 引入 ztree
24. 建立角色页面
25. 建立资源页面
26. 设置 shiro 过滤规则，登录功能
27. 更改 session 创建规则，设置服务器为无状态服务
28. 上传 git


### DEMO小样发展阶段二

1. 优化 js 插件
2. 完善用户增删改查功能
3. 完善角色增删改查功能
4. 完善资源增删改查功能
5. 添加上传文件功能
6. 添加导入 xml，解析 xml 功能
7. 添加注销功能
8. 上传 git


### DEMO小样发展阶段三

1. 优化 js 插件
2. 制作 index 页面
3. 对左侧菜单栏进行权限控制
4. 添加查找功能
5. 添加执行原生 SQL 工具类
6. 上传 git

### 作者
@at曾玉飞

### 邮箱
zengyufei@163.com

## 更多信息
https://github.com/zengyufei/springboot-myatis-shiro_sso-mapper3-swagger2-layui

### 博客
http://www.cnblogs.com/zengyufei/


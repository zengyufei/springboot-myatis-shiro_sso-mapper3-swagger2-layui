package com.zyf.framework.config;

import com.zyf.commons.support.mybatis.AutoMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * MyBatis扫描接口，使用的tk.mybatis.spring.dao.MapperScannerConfigurer <br/>
 * 如果你不使用通用Mapper，可以改为org.xxx...
 */
@Configuration
/*
	注意，由于MapperScannerConfigurer执行的比较早，
	所以必须有 @AutoConfigureAfter(MybatisAutoConfiguration.class) 注解,
	目的是让当前类在 MybatisAutoConfiguration 之后加载类
 */
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class MyBatisMapperScannerConfig {

	/**
	 * UUID：设置生成UUID的方法，需要用OGNL方式配置，不限制返回值，但是必须和字段类型匹配
	 * IDENTITY：取回主键的方式，可以配置的内容看下一篇如何使用中的介绍
	 * ORDER：<selectKey>中的order属性，可选值为BEFORE和AFTER
	 * catalog：数据库的catalog，如果设置该值，查询的时候表名会带catalog设置的前缀
	 * schema：同catalog，catalog优先级高于schema
	 * seqFormat：序列的获取规则,使用{num}格式化参数，默认值为{0}.nextval，针对Oracle，可选参数一共4个，对应0,1,2,3分别为SequenceName，ColumnName, PropertyName，TableName
	 * notEmpty：insert和update中，是否判断字符串类型!=''，少数方法会用到
	 * style：实体和表转换时的规则，默认驼峰转下划线，可选值为normal用实体名和字段名;camelhump是默认值，驼峰转下划线;uppercase转换为大写;lowercase转换为小写
	 * enableMethodAnnotation：可以控制是否支持方法上的JPA注解，默认false。
	 * <p>
	 * 实体类按照如下规则和数据库表进行转换,注解全部是JPA中的注解:
	 * 表名默认使用类名,驼峰转下划线(只对大写字母进行处理),如UserInfo默认对应的表名为user_info。
	 * 表名可以使用@Table(name = "tableName")进行指定,对不符合第一条默认规则的可以通过这种方式指定表名.
	 * 字段默认和@Column一样,都会作为表字段,表字段默认为Java对象的Field名字驼峰转下划线形式.
	 * 可以使用@Column(name = "fieldName")指定不符合第3条规则的字段名
	 * 使用@Transient注解可以忽略字段,添加该注解的字段不会作为表字段使用.
	 * 建议一定是有一个@Id注解作为主键的字段,可以有多个@Id注解的字段作为联合主键.
	 * 默认情况下,实体类中如果不存在包含@Id注解的字段,所有的字段都会作为主键字段进行使用(这种效率极低).
	 * 实体类可以继承使用,可以参考测试代码中的tk.mybatis.mapper.model.UserLogin2类.
	 * 由于基本类型,如int作为实体类字段时会有默认值0,而且无法消除,所以实体类中建议不要使用基本类型.
	 * \@NameStyle注解，用来配置对象名/字段和表名/字段之间的转换方式，该注解优先于全局配置style，可选值：
	 * normal:使用实体类名/属性名作为表名/字段名
	 * camelhump:这是默认值，驼峰转换为下划线形式
	 * uppercase:转换为大写
	 * lowercase:转换为小写
	 * 通过使用Mapper专用的MyBatis生成器插件可以直接生成符合要求带注解的实体类。
	 * 重点强调@Transient注解
	 * 许多人由于不仔细看文档，频繁在这个问题上出错。
	 * 如果你的实体类中包含了不是数据库表中的字段，你需要给这个字段加上@Transient注解，这样通用Mapper在处理单表操作时就不会将标注的属性当成表字段处理！
	 * <p>
	 * 主键策略(仅用于insert方法)
	 * 由于MySql自增主键最常用，所以这里从最简单的配置方式开始。
	 * 自增：
	 * mysql:@GeneratedValue(generator = "JDBC")
	 * 通用：
	 * \@GeneratedValue(strategy = GenerationType.IDENTITY)
	 * UUID：
	 * \@GeneratedValue(generator = "UUID")字段不会回写
	 * Oracle使用序列
	 * \@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select SEQ_ID.nextval from dual")
	 * 还需要在插件里配置<property name="ORDER" value="BEFORE"/>
	 * 可回写的 UUID
	 * \@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select uuid()")
	 * 并且注意要增加 ORDER=BEFORE （或 <property name="ORDER" value="BEFORE"/>）
	 * // 或在插件里配置
	 * IDENTITY=select uuid()
	 * ORDER=BEFORE
	 */
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		String scanPackagePath = "com.zyf.commons.mapper";
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage(scanPackagePath);
		Properties properties = new Properties();
		// 这里要特别注意，不要把MyMapper放到 basePackage 中，也就是不能同其他Mapper一样被扫描到。
		properties.setProperty("mappers", AutoMapper.class.getName());
		properties.setProperty("notEmpty", "false");
		properties.setProperty("IDENTITY", "MYSQL");
		properties.setProperty("style", "camelhump");
		/*
		新增了一个 settings 配置的参数 autoMappingUnknownColumnBehavior ，
		当检测出未知列（或未知属性）时，如何处理，默认情况下没有任何提示，这在测试的时候很不方便，不容易找到错误。
		可选值：
			NONE : 不做任何处理 (默认值)
			WARNING : 警告日志形式的详细信息
			FAILING : 映射失败，抛出异常和详细信息*/
		properties.setProperty("autoMappingUnknownColumnBehavior", "WARNING");
		mapperScannerConfigurer.setProperties(properties);
		return mapperScannerConfigurer;
	}

}

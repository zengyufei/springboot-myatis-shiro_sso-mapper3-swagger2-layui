package com.zyf.bval.test;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/*
		download & install: https://projectlombok.org/downloads/lombok.jar
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<version>1.16.14</version>
		</dependency>
 */
/*
@Data
public class User {
	@NotNull(result = "用户名不能为空",groups = ValidGroups.testGroup.class)
	private String username;
	@NotNull(result = "密码不能为null",groups = ValidGroups.testGroup.class)
	private String password;
	@Min(value = 10, result = "年龄的最小值为10",groups = ValidGroups.noGroup.class)
	private int age;
	@NotNull(result = "性别不能为null")
	private String sex;
}*/

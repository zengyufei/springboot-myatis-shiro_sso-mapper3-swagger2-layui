package com.zyf.bval.test;

import org.apache.bval.jsr.ApacheValidationProvider;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/*
	<repositories>
		<repository>
			<id>aliyun</id>
			<name>aliyun</name>
			<url>http://maven.aliyun.com/nexus/content/groups/public/</url>
			<layout>default</layout>
			<releases>
				<enabled>true</enabled>
			</releases>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
	</repositories>
 */
public class TestValidation {

	/*static Validator validator;

	@BeforeClass
	public static void testUp() {
		ValidatorFactory avf = Validation.byProvider(ApacheValidationProvider.class).configure().buildValidatorFactory();
		validator = avf.getValidator();
	}

	@Test
	public void testValid() {
		User user = new User();
		//user.setUsername("dsdsa");
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, ValidGroups.testGroup.class);
		assertNotNull(constraintViolations);
		System.out.println("---------- testValid1 start----------");
		foreach(constraintViolations.iterator());
	}

	@Test
	public void testValid2() {
		User user = new User();
		user.setUsername("dsdsa");
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, ValidGroups.noGroup.class);
		assertNotNull(constraintViolations);
		System.out.println("---------- testValid2 start----------");
		foreach(constraintViolations.iterator());
	}
	
	@Test
	public void testValid3() {
		User user = new User();
		user.setUsername("dsdsa");
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		assertNotNull(constraintViolations);
		System.out.println("---------- testValid3 start----------");
		foreach(constraintViolations.iterator());
	}

	private void foreach(Iterator iterator) {
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if(next instanceof ConstraintViolation)
				System.out.println(((ConstraintViolation) next).getMessage());
		}
	}*/
}

//result
/*
		---------- testValid1 start----------
		用户名不能为空
		密码不能为null
		---------- testValid2 start----------
		年龄的最小值为10
		---------- testValid3 start----------
		性别不能为null
 */
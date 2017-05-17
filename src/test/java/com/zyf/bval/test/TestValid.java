package com.zyf.bval.test;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class TestValid {

	/*@Test
	public void testQuery() {
		Validator v = MyValidatorFactory.SINGLE_INSTANCE.getValidator();
		User news = new User();
		news.setSex("男");
		Set<ConstraintViolation<User>> result = v.validate(news);
		assertTrue("应该通过校验", result.isEmpty());

		User news2 = new User();
		result = v.validate(news2);
		assertTrue("应该不通过校验", !result.isEmpty());

		System.out.println(result.size());
		for (ConstraintViolation<User> r : result) {
			System.out.println(r.getMessage());// 什么错？
			System.out.println(r.getPropertyPath());// 哪个字段错？
		}

	}*/
}  
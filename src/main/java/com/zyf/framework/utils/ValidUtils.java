package com.zyf.framework.utils;

import com.zyf.commons.result.Msg;
import org.apache.bval.jsr.ApacheValidationProvider;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * @className: 验证工具类
 * @author：zengyufei
 * @createTime: 2017年03月21日 16:35
 */

public class ValidUtils {

	private ValidUtils() {
	}

	public static Validator getInstance() {
		return Singleton.INSTANCE.getInstance();
	}

	private enum Singleton {
		INSTANCE;
		private Validator v;

		//JVM会保证此方法绝对只调用一次，保证单例执行线程安全。
		Singleton() {
			// BVal 与 JSR 接口结合，返回 ValidatorFactory 工厂
			ValidatorFactory b = Validation.byProvider(ApacheValidationProvider.class)
					.configure().buildValidatorFactory();
			v = b.getValidator();
		}

		public Validator getInstance() {
			return v;
		}
	}

	/**
	 * 验证数据
	 * 指定验证对象中的成员变量名称，但只包含 javax.validation.constraints.* 注解的成员变量
	 *
	 * @param t      待验证对象
	 * @param fields 待验证成员变量名称,多个用逗号隔开
	 * @param groups 按 group分组进行验证
	 * @return 错误提示集合
	 */
	public static <T> Msg validMsg(T t, String fields, Class<?>... groups) {
		Map<String, String> map = valid(t, fields, ",",  groups);
		if(!map.isEmpty()){
			for (Map.Entry<String, String> entry : map.entrySet()) {
				return Msg.getNewMsg(Msg.DEFAULT_CODE, entry.getValue());
			}
		}
		return Msg.getNewMsg();
	}

	/**
	 * 验证数据，通常用来验证字符串；
	 * 指定验证对象中的成员变量名称，但只包含 javax.validation.constraints.* 注解的成员变量
	 *
	 * @param clazz  待验证对象类型
	 * @param fields 待验证成员变量名称,多个用逗号隔开
	 * @param obj    待验证成员变量的具体值
	 * @return 错误提示集合
	 */
	public static <T> Msg validMsg(Class<?> clazz, String fields, Object... obj) {
		Map<String, String> map = valid(clazz, fields, ",", obj);
		Msg msg = new Msg();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			return msg.withError(Msg.DEFAULT_CODE, entry.getValue());
		}
		return msg;
	}

	/**
	 * 验证数据
	 * 指定验证对象中的成员变量名称，但只包含 javax.validation.constraints.* 注解的成员变量
	 *
	 * @param t         待验证对象
	 * @param fields    待验证成员变量名称,多个用指定分隔符隔开
	 * @param separator 指定分隔符
	 * @param objs      与fields分割后数量大小一致的对象集合
	 * @param groups    分组
	 * @return 错误提示集合
	 */
	private static <T> Map<String, String> valid(T t, String fields, String separator, Object[]
			objs, Class<?>... groups) {
		if (separator == null || "".equals(separator))
			throw new NullPointerException("Must have separator, separator not null or empty");
		if (fields == null || "".equals(fields))
			throw new NullPointerException("Must have fields, fields not null or empty");

		Map<String, String> map = new HashMap<>();
		String[] s = fields.split(separator);
		Validator v = ValidUtils.getInstance();
		for (int i = 0; i < s.length; i++) {
			Set<? extends ConstraintViolation<?>> cs = v.validateProperty(t, s[i].trim(), groups);
			for (ConstraintViolation<?> c : cs) {
				warpValidMessage(map, c);
			}
		}
		return map;
	}

	/**
	 * 验证数据
	 * 指定验证对象中的成员变量名称，但只包含 javax.validation.constraints.* 注解的成员变量
	 *
	 * @param clazz     待验证class，需要用到注解中的message
	 * @param fields    待验证成员变量名称,多个用指定分隔符隔开
	 * @param separator 指定分隔符
	 * @param objs      与fields分割后数量大小一致的对象集合
	 * @param groups    分组
	 * @return 错误提示集合
	 */
	private static <T> Map<String, String> valid(Class<?> clazz, String fields, String separator, Object[]
			objs, Class<?>... groups) {
		if (separator == null || "".equals(separator))
			throw new NullPointerException("Must have separator, separator not null or empty");
		if (fields == null || "".equals(fields))
			throw new NullPointerException("Must have fields, fields not null or empty");
		if (clazz == null)
			throw new NullPointerException("Must have clazzType, clazzType not null");

		String[] s = fields.split(separator);
		if (s.length != objs.length) {
			throw new IllegalArgumentException("The number of parameters and values is different");
		}

		Map<String, String> map = new HashMap<>();
		Validator v = ValidUtils.getInstance();
		for (int i = 0; i < s.length; i++) {
			Set<? extends ConstraintViolation<?>> cs = v.validateValue(clazz, s[i].trim(), objs[i], groups);
			for (ConstraintViolation<?> c : cs) {
				warpValidMessage(map, c);
			}
		}
		return map;
	}

	private static void warpValidMessage(Map<String, String> map, ConstraintViolation<?> c) {
		if (getAnnotationValue(c, NotNull.CODE) != null)
			map.put(NotNull.CODE, getAnnotationValue(c, NotNull.CODE).toString());

		if (getAnnotationValue(c, NotNull.FIELD_NAME) != null)
			map.put(NotNull.FIELD_NAME, getAnnotationValue(c, NotNull.FIELD_NAME).toString());

		if (getAnnotationValue(c, NotNull.MESSAGE) != null) {
			map.put(NotNull.MESSAGE, getAnnotationValue(c, NotNull.MESSAGE).toString());
		}
	}

	/**
	 * 返回原始bval验证对象自行处理
	 *
	 * @param t      验证的对象
	 * @param groups 分组
	 * @return key: getPropertyPath().toString(), value: getMsg()
	 */
	public <T> Set<ConstraintViolation<T>> validate(T t, Class<?>... groups) {
		Validator v = ValidUtils.getInstance();
		return v.validate(t, groups);
	}

	/**
	 * 获取实体注解上的属性，使用String key
	 * 接收一个ConstraintViolation
	 *
	 * @return Object
	 */

	public static <T> Object getAnnotationValue(ConstraintViolation<T> c, String key) {
		return c.getConstraintDescriptor().getAttributes().get(key);
	}

}



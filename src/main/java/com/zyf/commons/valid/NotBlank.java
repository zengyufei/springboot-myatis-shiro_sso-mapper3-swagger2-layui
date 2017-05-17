package com.zyf.commons.valid;

import com.zyf.commons.valid.handler.NotBlankValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @className: 字符串判空验证
 * @classDesc:
 * @author：zengyufei
 * @createTime: 2017年03月22日 14:06
 * www.zzsim.com
 */

@Documented
@Constraint(
		validatedBy = {NotBlankValidator.class}
)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlank {

	String CODE = "code";
	String FIELD_NAME = "fieldName";
	String MESSAGE = "result";
	String GROUPS = "groups";

	String code() default "";

	String fieldName() default "";

	Class<?>[] groups() default {};

	String message() default "{com.zzsim.common.valid.NotBlank.result}";

	Class<? extends Payload>[] payload() default {};

	@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface List {
		NotBlank[] value();
	}
}

package com.zyf.commons.valid.handler;


import com.zyf.commons.valid.NotBlank;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotBlankValidator implements ConstraintValidator<NotBlank, Object> {
	public NotBlankValidator() {
	}

	public void initialize(NotBlank constraintAnnotation) {
	}

	public boolean isValid(Object value, ConstraintValidatorContext context) {
		return value != null && !value.toString().isEmpty();
	}
}
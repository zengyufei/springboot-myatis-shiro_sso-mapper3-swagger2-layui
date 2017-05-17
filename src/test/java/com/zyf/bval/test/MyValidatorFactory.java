package com.zyf.bval.test;

import org.apache.bval.jsr.ApacheValidationProvider;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public enum MyValidatorFactory {
	SINGLE_INSTANCE {
		// BVal 与 JSR 接口结合，返回 ValidatorFactory 工厂
		ValidatorFactory avf = Validation.byProvider(ApacheValidationProvider.class).configure().buildValidatorFactory();
		@Override
		public Validator getValidator() {
			return avf.getValidator();
		}
	};

	/**
	 * 返回一个校验器
	 * @return 校验器
	 */
	public abstract Validator getValidator();
}  
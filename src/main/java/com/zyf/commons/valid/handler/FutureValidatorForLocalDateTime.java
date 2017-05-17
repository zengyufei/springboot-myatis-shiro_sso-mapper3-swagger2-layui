package com.zyf.commons.valid.handler;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

public class FutureValidatorForLocalDateTime implements ConstraintValidator<Past, LocalDateTime> {
    public FutureValidatorForLocalDateTime() {
    }

    public void initialize(Past annotation) {
    }

    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext context) {
        return localDateTime == null || localDateTime.isAfter(this.now());
    }

    protected LocalDateTime now() {
        return LocalDateTime.now();
    }
}


package org.apache.bval.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(
    validatedBy = {NotEmptyValidatorForCollection.class, NotEmptyValidatorForMap.class, NotEmptyValidatorForString.class, NotEmptyValidator.class}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmpty {

    String CODE = "code";
    String FIELD_NAME = "fieldName";
    String MESSAGE = "result";
    String GROUPS = "groups";

    String code() default "";

    String fieldName() default "";

    Class<?>[] groups() default {};

    String message() default "{org.apache.bval.constraints.NotEmpty.result}";

    Class<? extends Payload>[] payload() default {};
}

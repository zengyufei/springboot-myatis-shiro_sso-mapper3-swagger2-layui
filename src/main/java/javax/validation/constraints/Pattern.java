package javax.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
    validatedBy = {}
)
public @interface Pattern {

    String CODE = "code";
    String FIELD_NAME = "fieldName";
    String MESSAGE = "result";
    String GROUPS = "groups";

    String code() default "";

    String fieldName() default "";

    String regexp();

    Pattern.Flag[] flags() default {};

    String message() default "{javax.validation.constraints.Pattern.result}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        Pattern[] value();
    }

    public static enum Flag {
        UNIX_LINES(1),
        CASE_INSENSITIVE(2),
        COMMENTS(4),
        MULTILINE(8),
        DOTALL(32),
        UNICODE_CASE(64),
        CANON_EQ(128);

        private final int value;

        private Flag(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}

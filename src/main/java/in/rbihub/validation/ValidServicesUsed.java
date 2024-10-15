package in.rbihub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidServicesUsedValidator.class)
public @interface ValidServicesUsed {
    String message() default "{services_used.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

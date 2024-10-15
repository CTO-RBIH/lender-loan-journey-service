package in.rbihub.validation;

import in.rbihub.validation.DateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateFormat {
    String message() default "{disbursement_date.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String pattern() default "yyyy-MM-dd"; // Default date pattern
}

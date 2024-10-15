package in.rbihub.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeFormatValidator.class)
public @interface ValidAgeFormat {
    String message() default "{age.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

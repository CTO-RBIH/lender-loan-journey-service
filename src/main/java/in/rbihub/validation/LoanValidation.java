package in.rbihub.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LoanRecordValidation.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoanValidation {
    String message() default "{loan_validation.invalid}"; // Default message
    String activeStatusMessage() default "{active_status_Y_validation.invalid}"; // Active status validation message
    String reasonWithdrawalMessage() default "{reason_for_withdrawal_validation.invalid}"; // Reason for withdrawal validation message

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

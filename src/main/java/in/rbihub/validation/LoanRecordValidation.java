package in.rbihub.validation;

import in.rbihub.entity.LenderLoanRecordEntity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LoanRecordValidation implements ConstraintValidator<LoanValidation, LenderLoanRecordEntity> {

    @Override
    public boolean isValid(LenderLoanRecordEntity entity, ConstraintValidatorContext context) {
        boolean isValid = true; // Track overall validity

        // Validate timestamps
        if (entity.getJourneyStartTime() == null || entity.getLoanSanctionTime() == null) {
            return true; // If either timestamp is null, we won't validate.
        }

//        if (!entity.getJourneyStartTime().before(entity.getLoanSanctionTime())) {
//            context.buildConstraintViolationWithTemplate("{loan_validation.invalid}")
//                    .addPropertyNode("applicationStartTimestamp")
//                    .addConstraintViolation();
//            isValid = false; // Set overall validity to false
//        }

        // Validate reason for withdrawal based on active status
//        if ("Y".equals(entity.getActiveStatus())) {
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate("{active_status_Y_validation.invalid}")
//                    .addPropertyNode("reasonForWithdrawal")
//                    .addConstraintViolation();
//            isValid = false; // Set overall validity to false
//        }

        return isValid; // Return overall validity
    }
}

package in.rbihub.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeFormatValidator implements ConstraintValidator<ValidAgeFormat, String> {

    @Override
    public void initialize(ValidAgeFormat ageFormat) {
        // Any initialization if needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false; // Age is mandatory
        }

        // Validate the format using a regular expression
        String regex = "^(\\d{1,3})\\.(\\d{1,2})$"; // Matches "xx.xx" format
        if (!value.matches(regex)) {
            return false;
        }

        try {
            // Split into integer and decimal parts
            String[] parts = value.split("\\.");
            int integerPart = Integer.parseInt(parts[0]);
            int decimalPart = Integer.parseInt(parts[1]);

            // Ensure integer part is between 0 and 100, and decimal part is between 0 and 11
            return integerPart >= 0 && integerPart <= 100 && decimalPart >= 0 && decimalPart <= 11;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

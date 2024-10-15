package in.rbihub.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidator implements ConstraintValidator<ValidDateFormat, String> {

    private String pattern;

    @Override
    public void initialize(ValidDateFormat constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        if (date == null) {
            return true; // Let @NotNull handle null values
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setLenient(false); // This ensures strict parsing (e.g., no leniency for dates like 2024-13-40)

        try {
            // Parse the date to validate its format
            sdf.parse(date);
        } catch (ParseException e) {
            return false; // Invalid date format
        }

        return true; // Valid date format
    }
}

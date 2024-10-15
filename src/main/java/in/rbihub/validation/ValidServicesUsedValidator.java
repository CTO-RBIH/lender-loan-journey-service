package in.rbihub.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class ValidServicesUsedValidator implements ConstraintValidator<ValidServicesUsed, String[]> {

    private static final List<String> VALID_SERVICES = Arrays.asList(
            "satsourcereport", "satsourcereportmultiland", "aadhaaresignprt", "panverification", "aaconsent",
            "propsearchteal", "getconsentformilkinsights", "digilocker/issued-documents", "cgtmse/signin",
            "cgtmse/borrowereligibility", "cgtmse/guaranteelodgement", "cgtmse/enapiresult", "cgtmse/disbursementupdate",
            "cgtmse/npalodgement", "cgtmse/npaupgrade", "cgtmse/outstanding","bbpsdps", "ibdic/invoice-registration-without-entity-code",
            "stellapps/consent", "lrsmasterdata"
    );

    @Override
    public boolean isValid(String[] servicesUsed, ConstraintValidatorContext context) {
        if (servicesUsed == null || servicesUsed.length == 0) {
            return false; // Ensure non-null and non-empty
        }

        // Validate each service used (case insensitive)
        for (String service : servicesUsed) {
            if (VALID_SERVICES.stream().noneMatch(validService -> validService.equalsIgnoreCase(service))) {
                return false; // Invalid service found
            }
        }
        return true;
    }
}

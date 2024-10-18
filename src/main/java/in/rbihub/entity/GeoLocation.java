package in.rbihub.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Embeddable
@Data
public class GeoLocation {

    @NotNull(message = "{latitude.invalid}")
    @DecimalMin(value = "-90.0", inclusive = true, message = "{latitude.invalid}")
    @DecimalMax(value = "90.0", inclusive = true, message = "{latitude.invalid}")
    private Double latitude;

    @NotNull(message = "{longitude.invalid}")
    @DecimalMin(value = "-180.0", inclusive = true, message = "{longitude.invalid}")
    @DecimalMax(value = "180.0", inclusive = true, message = "{longitude.invalid}")
    private Double longitude;
}

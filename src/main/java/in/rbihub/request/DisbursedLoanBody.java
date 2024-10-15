package in.rbihub.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.rbihub.common.request.Body;
import in.rbihub.entity.DisbursedLoanEntity;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class DisbursedLoanBody extends Body {

    @Valid
    @JsonProperty("data")
    private DisbursedLoanEntity data;
}

package in.rbihub.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.rbihub.common.request.Body;
import in.rbihub.entity.LenderLoanRecordEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class LenderLoanRecordBody extends Body {

    @Valid
    @JsonProperty("data")
    LenderLoanRecordEntity data;
}

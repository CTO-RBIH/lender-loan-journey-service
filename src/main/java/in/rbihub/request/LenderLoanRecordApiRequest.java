package in.rbihub.request;

import in.rbihub.common.request.ApiRequest;
import in.rbihub.entity.LenderLoanRecordEntity;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringExclude;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class LenderLoanRecordApiRequest extends ApiRequest {
    @Valid
    LenderLoanRecordBody body;
}

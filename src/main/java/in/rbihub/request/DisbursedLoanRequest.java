package in.rbihub.request;

import in.rbihub.common.request.ApiRequest;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class DisbursedLoanRequest extends ApiRequest {
    @Valid
    private DisbursedLoanBody body;
}

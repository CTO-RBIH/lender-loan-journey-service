package in.rbihub.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.rbihub.common.request.ApiRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class RejectedLoanRecordApiRequest extends ApiRequest {

    RejectedLoanRecordBody body;

}

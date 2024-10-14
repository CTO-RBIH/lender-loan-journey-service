package in.rbihub.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LenderLoanRecordWithdrawalEntity {

    @JsonProperty("loan_id")
    private String loanId;
    @JsonProperty("reason_for_rejection")
    private String reason_for_rejection;
}

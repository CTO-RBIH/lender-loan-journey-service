package in.rbihub.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LenderLoanRecordWithdrawalEntity {

    @JsonProperty("loan_id")
    private String loanId;
    @JsonProperty("withdrawal_reason")
    private String withdrawalReason;

    @JsonProperty("customer_id")
    private String customerId;
}

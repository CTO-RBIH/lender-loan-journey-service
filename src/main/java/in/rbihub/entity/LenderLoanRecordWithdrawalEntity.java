package in.rbihub.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LenderLoanRecordWithdrawalEntity {

    @JsonProperty("loan_id")
    private String loanId;

    @JsonProperty("withdrawal_reason")
    private Integer withdrawalReason;  // Withdrawal reason (integer between 1 and 5)
}

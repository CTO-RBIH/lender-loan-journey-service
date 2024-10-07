package in.rbihub.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RejectedLoanRecordBodyData {


    @JsonProperty("loan_type")
    private String loanType;

    @JsonProperty("lender_name")
    private String lenderName;

    @JsonProperty("loan_disbursed_amount")
    private Double loanDisbursedAmount;

    @JsonProperty("district")
    private String district;

    @JsonProperty("state")
    private String state;

    @JsonProperty("branch_code")
    private String branchCode;

    @JsonProperty("pin_code")
    private String pinCode;

    @JsonProperty("ifsc_code")
    private String ifscCode;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("application_start_timestamp")
    private String applicationStartTimestamp;  // String to be converted into Timestamp

    @JsonProperty("rejection_timestamp")
    private String rejectionTimestamp;  // String to be converted into Timestamp

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("loan_channel")
    private String loanChannel;

    @JsonProperty("reason_for_rejection")
    private String reasonForRejection;

}



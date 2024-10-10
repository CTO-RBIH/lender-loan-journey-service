package in.rbihub.entity;

import in.rbihub.validation.LoanValidation; // Import the custom annotation
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@IdClass(LenderLoanRecordId.class)
@Table(name = "lender_loan_record")
@LoanValidation // Apply the custom annotation here
public class LenderLoanRecordEntity {

    // You can add setters here if needed
    // Use this for setting original loanId if needed
    @Setter
    @Id
    @Column(name = "loan_id", nullable = false)
    @NotBlank(message = "{loan_id.invalid}") // Error code for blank loan ID
    private String loanId;

    // Use this for setting original clientId if needed
    @Setter
    @Id
    @Column(name = "client_id", nullable = false)
    @NotBlank(message = "{client_id.invalid}") // Error code for blank client ID
    private String clientId;

    @NotBlank(message = "{lender_name.invalid}") // Error code for blank lender name
    private String lenderName;  // Bank name (Mandatory)

    @NotNull(message = "{loan_type.invalid}") // Error code for null loan type
    @Min(value = 1, message = "{loan_type.invalid}") // Error code for minimum loan type
    @Max(value = 10, message = "{loan_type.invalid}") // Error code for maximum loan type
    private Integer loanType;  // Loan type (Mandatory, numeric format between 1-10)

    @NotNull(message = "{sanctioned_amount.invalid}") // Error code for null sanctioned amount
    @DecimalMin(value = "0.0", inclusive = false, message = "{sanctioned_amount.invalid}") // Error code for negative or zero amount
    private Double sanctionedAmount;  // Loan Disbursed Amount (Mandatory)

    @NotNull(message = "{district.invalid}") // Error code for blank district, state, and pincode
    @Pattern(regexp = ".+", message = "{district.invalid}") // Ensure values for district, state, and pincode
    private String district;  // District (Mandatory)

    @NotBlank(message = "{state.invalid}") // Error code for blank state
    private String state;  // State (Mandatory)

    @Pattern(regexp = "\\d{6}", message = "{pincode.invalid}") // Error code for pincode format
    private String pincode;  // Pincode (Mandatory, 6 digits)

    @Pattern(regexp = "[A-Z]{4}0[A-Z0-9]{6}", message = "{ifsc_code.invalid}") // Error code for IFSC code format
    private String ifscCode;  // IFSC code (Mandatory)

    @NotBlank(message = "{branch_code.invalid}") // Error code for blank branch code
    @Column(name = "branch_code", nullable = false) // Ensure this is nullable = false
    private String branchCode;  // Branch Code (Mandatory)

    @NotNull(message = "{gender.invalid}") // Error code for null gender
    @Pattern(regexp = "M|F|T", message = "{gender.invalid}") // Error code for gender validation
    private String gender;  // Gender (M/F/T) (Mandatory)

    @NotNull(message = "{application_start_timestamp.invalid}") // Error code for null application start timestamp
    private Timestamp applicationStartTimestamp;  // Application Start Timestamp (Mandatory)

    @NotNull(message = "{loan_sanction_timestamp.invalid}") // Error code for null loan sanction timestamp
    private Timestamp loanSanctionTimestamp;  // Loan Sanction Timestamp (Mandatory)

    @Column(nullable = true)
    private String loanProductName;  // Product Name (Optional)

    @NotNull(message = "{age.invalid}") // Error code for null age
    @Min(value = 18, message = "{age.invalid}") // Error code for minimum age
    @Max(value = 100, message = "{age.invalid}") // Error code for maximum age
    private Integer age;  // Age (Mandatory)

    @NotBlank(message = "{loan_channel.invalid}") // Error code for blank loan channel
    private String loanChannel;  // Loan Channel (Mandatory)

    @NotBlank(message = "{marital_status.invalid}") // Error code for blank marital status
    @Pattern(regexp = "S|M|D|W", message = "{marital_status.invalid}") // Error code for marital status validation
    private String maritalStatus;  // Marital Status (Mandatory)

    @NotNull(message = "{annual_income.invalid}") // Error code for null annual income
    @DecimalMin(value = "0.0", inclusive = false, message = "{annual_income.invalid}") // Error code for negative or zero income
    private Double annualIncome;  // Annual Income (Mandatory)

    @NotBlank(message = "{device_type.invalid}") // Error code for blank device type
    @Pattern(regexp = "M|D", message = "{device_type.invalid}") // Error code for device type validation
    private String deviceType;  // Device Type (Mandatory)

    @NotNull(message = "{professional_background.invalid}") // Error code for null profession
    @Pattern(regexp = "S|E|I|N", message = "{professional_background.invalid}") // Error code for profession validation
    private String professionalBackground;  // Profession (Mandatory)

    @Min(value = 1, message = "{educational_background.invalid}") // Error code for minimum educational background
    @Max(value = 6, message = "{educational_background.invalid}") // Error code for maximum educational background
    private int educationalBackground; // Educational Background (Range validation)

    @NotNull(message = "{state_code.invalid}") // Error code for state, district, sub district, and village codes
    @Pattern(regexp = "\\d{2}|0000", message = "{state_code.invalid}") // Ensure valid state code
    private String stateCode;  // State Code (Mandatory)

    @Pattern(regexp = "\\d{3}|0000", message = "{district_code.invalid}") // Ensure valid district code
    private String districtCode;  // District Code (Mandatory)

    @Pattern(regexp = "\\d{4}|0000", message = "{sub_district_code.invalid}") // Ensure valid sub district code
    private String subDistrictCode;  // Sub District Code (Mandatory)

    @Pattern(regexp = "\\d{6}|0000", message = "{village_code.invalid}") // Ensure valid village code
    private String villageCode;  // Village Code (Mandatory)

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;  // Created At (Autogenerated)

    @Column(name = "active_status", nullable = false)
    @Pattern(regexp = "Y|N", message = "{active_status.invalid}") // Error code for active status validation
    private String activeStatus = "Y";

    @NotNull(message = "{services_used.invalid}") // Error code for null services used
    @Column(name = "services_used", nullable = false)
    private String[] servicesUsed;  // Array of strings for services used (Mandatory)

    @Column(name = "updated_at", nullable = false)
    @org.hibernate.annotations.UpdateTimestamp
    private Timestamp updatedAt;

    @Column(name = "reason_for_withdrawal", nullable = false)
    @NotNull(message = "{reason_for_withdrawal.invalid}") // Error code for reason for withdrawal
    private String reasonForWithdrawal = "0000"; // Default value

    // Ensure you have a way to set hashed IDs in the loanRecord entity
    public void setHashedId(String loanId, String clientId) {
        this.loanId = loanId;  // Set the hashed loanId
        this.clientId = clientId;  // Set the hashed clientId
    }
}

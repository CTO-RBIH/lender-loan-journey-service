package in.rbihub.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import in.rbihub.validation.ValidDateFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "disbursement_loan_record")
public class DisbursedLoanEntity {

    // Primary Key: Unique Disbursement ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremented ID for each unique disbursement
    @Column(name = "disbursement_id", updatable = false, nullable = false)
    private Long disbursementId;

    // Loan ID (each entry can have the same loan ID but different disbursements)
    @Column(name = "loan_id", nullable = false)
    @NotBlank(message = "{loan_id.invalid}") // Error for blank loan ID
    private String loanId;

    // Sanctioned Amount (Optional)
    @Column(name = "sanctioned_amount", nullable = true)
    @DecimalMin(value = "0.0", inclusive = false, message = "{sanctioned_amount.invalid}") // Sanctioned amount cannot be negative
    private BigDecimal sanctionedAmount;

    // Total Tranches (Mandatory)
    @Column(name = "total_tranches", nullable = false)
    @Min(value = 1, message = "{total_tranches.invalid}") // Minimum value for tranches
    @NotNull(message = "{total_tranches.invalid}")
    private Integer totalTranches;

    // Change to String and apply custom date validation
    @Column(name = "disbursement_date", nullable = false)
    @NotNull(message = "{disbursement_date.invalid}")
    @ValidDateFormat(message = "{disbursement_date.invalid}")  // Custom annotation for date format validation
    private String disbursementDate;

    // Amount Disbursed (Mandatory, should be less than or equal to sanctioned amount)
    @Column(name = "amount_disbursed", nullable = false)
    @NotNull(message = "{amount_disbursed.invalid}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{amount_disbursed.invalid}") // Amount disbursed should be positive
    private BigDecimal amountDisbursed;

//    // Tranche Count (Mandatory, default value 1)
//    @Column(name = "tranche_count", nullable = false)
//    @Min(value = 1, message = "{tranche_count.invalid}") // Minimum tranche count is 1
//    @NotNull(message = "{tranche_count.invalid}")
//    private Integer trancheCount;

    // Tranche Count (Automatically handled by service)
    @Column(name = "tranche_count", nullable = false)
    private Integer trancheCount;

    // Automatically sets the creation timestamp when the record is created
    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;
}

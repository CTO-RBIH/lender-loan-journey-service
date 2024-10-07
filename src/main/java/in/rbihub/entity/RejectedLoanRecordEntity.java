package in.rbihub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "rejected_loan_record")  // New table name for rejected loans
public class RejectedLoanRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;  // Loan ID

    private String loanType;  // Loan Type
    private String lenderName;  // Bank Name

    private String district;  // District
    private String state;  // State
    private String branchCode;  // Branch Code
    private String pincode;  // Pincode
    private String ifscCode;  // IFSC Code
    private String gender;  // Gender (M/F/T)

    private Double amount;  // Requested Amount

    private Timestamp applicationStartTimestamp;  // Application Start Time
    private Timestamp rejectionTimestamp;  // Rejection Timestamp

    @Column(nullable = true)  // Optional field
    private String productName;  // Product Name (optional)

    private Integer age;  // Borrower's Age
    private String loanChannel;  // Loan Channel

    private String reasonForRejection;  // Reason for Rejection
}

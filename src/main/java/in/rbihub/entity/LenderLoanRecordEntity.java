package in.rbihub.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;


@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "lender_loan_record")
public class LenderLoanRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String loanType;
    private String lenderName;
    private Date dateOfReporting;
    @Column(name = "loans_count_sa_or_digital_model")
    private Long loansCountSelfAssistedOrDigitalModel;
    private Long loansCountBcModel;
    private Long loansCountBranchModel;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private TotalApplicationsReceived totalApplicationsReceived;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private NoOfLoansApproved noOfLoansApproved;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private NoOfLoansRejected noOfLoansRejected;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private LoanDisbursedAmount loanDisbursedAmount;
    private Long averageTat;
    @Column(name = "created_at", updatable = false)
    Timestamp createdAt;
}

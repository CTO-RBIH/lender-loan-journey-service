package in.rbihub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LenderLoanRecordId implements Serializable {
    // Column representing 'loan_id' in the database
    @Column(name = "loan_id")
    private String loanId;

    // Column representing 'client_id' in the database
    @Column(name = "client_id")
    private String clientId;

    // Default constructor required by JPA
    public LenderLoanRecordId() {
    }

    // Parameterized constructor for convenience
    public LenderLoanRecordId(String loanId, String clientId) {
        this.loanId = loanId;
        this.clientId = clientId;
    }

    // Override equals method for proper comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LenderLoanRecordId that = (LenderLoanRecordId) o;
        return loanId.equals(that.loanId) && clientId.equals(that.clientId);
    }

    // Override hashCode method for consistency with equals
    @Override
    public int hashCode() {
        return Objects.hash(loanId, clientId);
    }
}

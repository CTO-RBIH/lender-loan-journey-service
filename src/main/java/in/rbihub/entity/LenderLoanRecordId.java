package in.rbihub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static in.rbihub.utils.LenderLoanJourneyUtils.hash;

@Getter
@Embeddable
public class LenderLoanRecordId implements Serializable {

    @Column(name = "loan_id")
    private String loanId;

    @Column(name = "customer_id")
    private String customerId;

    // Default constructor required by JPA
    public LenderLoanRecordId() {
    }

    // Parameterized constructor for convenience
    public LenderLoanRecordId(String loanId, String clientId) {
        this.loanId = hash(loanId);  // Hash the loanId
        this.customerId = hash(clientId);  // Hash the clientId
    }

    // Override equals method for proper comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LenderLoanRecordId that = (LenderLoanRecordId) o;
        return loanId.equals(that.loanId) && customerId.equals(that.customerId);
    }

    // Override hashCode method for consistency with equals
    @Override
    public int hashCode() {
        return Objects.hash(loanId, customerId);
    }
}

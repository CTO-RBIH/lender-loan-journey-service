package in.rbihub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Getter
@Embeddable
public class LenderLoanRecordId implements Serializable {

    @Column(name = "loan_id")
    private String loanId;

    @Column(name = "client_id")
    private String clientId;

    // Default constructor required by JPA
    public LenderLoanRecordId() {
    }

    // Parameterized constructor for convenience
    public LenderLoanRecordId(String loanId, String clientId) {
        this.loanId = hash(loanId);  // Hash the loanId
        this.clientId = hash(clientId);  // Hash the clientId
    }

    // Hashing method using SHA-256
    private String hash(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input to hash cannot be null");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing input with SHA-256", e);
        }
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

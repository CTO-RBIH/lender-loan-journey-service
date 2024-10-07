package in.rbihub.repository;

import in.rbihub.entity.RejectedLoanRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RejectedLoanRecordRepository extends JpaRepository<RejectedLoanRecordEntity, UUID> {
    // You can add custom queries here if needed
}

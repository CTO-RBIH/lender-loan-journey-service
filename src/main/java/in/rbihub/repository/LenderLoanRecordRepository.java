package in.rbihub.repository;

import in.rbihub.entity.LenderLoanRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LenderLoanRecordRepository extends JpaRepository<LenderLoanRecordEntity, UUID> {
}

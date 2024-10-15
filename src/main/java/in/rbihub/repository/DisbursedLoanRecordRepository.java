package in.rbihub.repository;

import in.rbihub.entity.DisbursedLoanEntity;
import in.rbihub.entity.LenderLoanRecordEntity;
import in.rbihub.entity.LenderLoanRecordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface DisbursedLoanRecordRepository extends JpaRepository<DisbursedLoanEntity, Long> {

    // Custom query to find the latest disbursement by loan_id, ordered by tranche_count
    Optional<DisbursedLoanEntity> findTopByLoanIdOrderByTrancheCountDesc(String loanId);
}
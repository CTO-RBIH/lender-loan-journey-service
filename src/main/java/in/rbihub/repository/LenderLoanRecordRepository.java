package in.rbihub.repository;

import in.rbihub.entity.LenderLoanRecordEntity;
import in.rbihub.entity.LenderLoanRecordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LenderLoanRecordRepository extends JpaRepository<LenderLoanRecordEntity, LenderLoanRecordId> {
    Optional<LenderLoanRecordEntity> findByLoanId(String loanId);
}


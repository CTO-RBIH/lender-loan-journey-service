package in.rbihub.repository;

import in.rbihub.entity.LenderJourneyMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LenderJourneyMetadataRepository extends JpaRepository<LenderJourneyMetadataEntity, Long> {
    @Query("SELECT m.metadataValues FROM LenderJourneyMetadataEntity m WHERE m.metadataKey = :metadataKey")
    String findMetadataValuesByMetadataKey(@Param("metadataKey") String metadataKey);
}

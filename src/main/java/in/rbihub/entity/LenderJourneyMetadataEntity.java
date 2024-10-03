package in.rbihub.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "lender_journey_metadata")
public class LenderJourneyMetadataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "metadata_key")
    private String metadataKey;

    @Column(name = "metadata_values")
    private String metadataValues;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

}

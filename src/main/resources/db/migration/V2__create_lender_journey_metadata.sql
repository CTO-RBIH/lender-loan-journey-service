CREATE TABLE lender_journey_metadata (
    id BIGSERIAL PRIMARY KEY,
    metadata_key text NOT NULL UNIQUE,
    metadata_values TEXT,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--Todo add golive date of lenders
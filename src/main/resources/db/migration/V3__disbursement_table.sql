CREATE TABLE disbursement_loan_record (
    disbursement_id SERIAL PRIMARY KEY, -- Auto-incrementing primary key for each disbursement entry
    loan_id VARCHAR(255) NOT NULL, -- Loan ID (can have multiple records with the same loan ID)
    sanctioned_amount DECIMAL(15, 2), -- Sanctioned amount (optional)
    total_tranches INT NOT NULL CHECK (total_tranches >= 1), -- Total tranches (must be >= 1)
    disbursement_date VARCHAR(11) NOT NULL, -- Date of disbursement
    amount_disbursed DECIMAL(15, 2) NOT NULL CHECK (amount_disbursed >= 0), -- Amount disbursed (must be >= 0)
--    tranche_count INT NOT NULL DEFAULT 1 CHECK (tranche_count >= 1), -- Tranche count (must be >= 1)
    tranche_count INT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp for when the record is created
    CONSTRAINT amount_check CHECK (amount_disbursed <= sanctioned_amount) -- Check to ensure disbursed amount <= sanctioned amount
);


INSERT INTO disbursement_loan_record (
    loan_id, sanctioned_amount, total_tranches, disbursement_date, amount_disbursed, tranche_count
)
VALUES
    (hash_sha256('LN123456'), 1000000.00, 5, '2024-10-15', 200000.00, 1),
    (hash_sha256('LN123456'), 1000000.00, 5, '2024-11-01', 300000.00, 2),
    (hash_sha256('LN789012'), 500000.00, 3, '2024-10-20', 150000.00, 1);

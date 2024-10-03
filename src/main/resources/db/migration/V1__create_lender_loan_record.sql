CREATE TABLE lender_loan_record (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_type VARCHAR(255),
    lender_name VARCHAR(255),
    date_of_reporting DATE,
    loans_count_sa_or_digital_model BIGINT,
    loans_count_bc_model BIGINT,
    loans_count_branch_model BIGINT,
    total_applications_received JSONB,
    no_of_loans_approved JSONB,
    no_of_loans_rejected JSONB,
    loan_disbursed_amount JSONB,
    average_tat BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
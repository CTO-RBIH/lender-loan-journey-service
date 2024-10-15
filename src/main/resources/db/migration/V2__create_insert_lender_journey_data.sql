CREATE TABLE lender_loan_record (
    loan_id VARCHAR(255) NOT NULL,
    lender_name VARCHAR(255) NOT NULL,
    loan_type INT NOT NULL CHECK (loan_type BETWEEN 1 AND 10),
    product_name VARCHAR(255),
    sanctioned_amount NUMERIC NOT NULL CHECK (sanctioned_amount > 0),
    loan_channel VARCHAR(10) NOT NULL CHECK (loan_channel IN ('1', '2', '3')),
    branch_code VARCHAR(255) NOT NULL,
    pincode VARCHAR(10) NOT NULL,
    ifsc_code VARCHAR(11) NOT NULL,
    state_code VARCHAR(10) NOT NULL,
    district_code VARCHAR(10) NOT NULL,
    sub_district_code VARCHAR(10) NOT NULL DEFAULT '0000',
    village_lgd_code VARCHAR(10) NOT NULL DEFAULT '0000',
    gender CHAR(1) NOT NULL CHECK (gender IN ('M', 'F', 'T')),
    age VARCHAR(5) NOT NULL,
    marital_status CHAR(1) NOT NULL CHECK (marital_status IN ('S', 'M', 'D', 'W')),
    annual_income NUMERIC NOT NULL CHECK (annual_income >= 0),
    edu_background INT NOT NULL CHECK (edu_background BETWEEN 1 AND 6),
    professional_background CHAR(1) NOT NULL CHECK (professional_background IN ('S', 'E', 'I', 'N')),
    journey_start_time TIMESTAMP NOT NULL,
    loan_sanction_time TIMESTAMP NOT NULL,
    device_type CHAR(1) NOT NULL CHECK (device_type IN ('M', 'D')),
    active_status CHAR(1) NOT NULL DEFAULT 'Y',
    services_used TEXT[],  -- Ensure this is an array type
    reason_for_withdrawal VARCHAR(10) NOT NULL DEFAULT '0000',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (loan_id)
);

CREATE OR REPLACE FUNCTION hash_sha256(input TEXT) RETURNS TEXT AS $$
DECLARE
    hashed_output TEXT;
BEGIN
    SELECT encode(digest(input, 'sha256'), 'hex') INTO hashed_output;
    RETURN hashed_output;
END;
$$ LANGUAGE plpgsql;


-- Insert statement rearranged to match the new column order
INSERT INTO lender_loan_record (
    loan_id, lender_name, loan_type, product_name, sanctioned_amount, loan_channel, branch_code,
    pincode, ifsc_code, state_code, district_code, sub_district_code, village_lgd_code, gender, age,
    marital_status, annual_income, edu_background, professional_background, journey_start_time,
    loan_sanction_time, device_type, active_status, services_used,
    reason_for_withdrawal
)
VALUES
(
    hash_sha256('L-102'),          -- Hash the loan_id
    'ICICI Bank',
    2,
    'Home Loan',
    500000,
    '2',
    'BR002',
    '560001',
    'ICIC0001234',
    '27',
    '4002',
    '0000',
    '0000',
    'F',
    28.0,
    'M',
    600000,
    3,
    'E',
    '2024-07-15 10:00:00',
    '2024-07-15 14:00:00',
    'D',
    'Y',
    ARRAY['Home Application', 'Home Sanction'],
    '0000'
);
-- Create a function to update the 'updated_at' column to the current timestamp
CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
   NEW.updated_at = now();  -- Set 'updated_at' to the current date and time
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create a trigger that invokes the above function before any update on 'lender_loan_record'
CREATE TRIGGER update_lender_loan_record_modtime
BEFORE UPDATE ON lender_loan_record
FOR EACH ROW
EXECUTE FUNCTION update_modified_column();


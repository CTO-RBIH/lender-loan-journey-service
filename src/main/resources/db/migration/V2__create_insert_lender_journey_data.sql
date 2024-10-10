CREATE TABLE lender_loan_record (
    loan_id VARCHAR(255) NOT NULL,
    client_id VARCHAR(255) NOT NULL,
    lender_name VARCHAR(255) NOT NULL,
    loan_type INT NOT NULL CHECK (loan_type BETWEEN 1 AND 10),
    loan_product_name VARCHAR(255),
    sanctioned_amount NUMERIC NOT NULL CHECK (sanctioned_amount > 0),
    loan_channel VARCHAR(10) NOT NULL CHECK (loan_channel IN ('1', '2', '3')),
    district VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    branch_code VARCHAR(255) NOT NULL,
    pincode VARCHAR(10) NOT NULL,
    ifsc_code VARCHAR(11) NOT NULL,
    state_code VARCHAR(10) NOT NULL,
    district_code VARCHAR(10) NOT NULL,
    sub_district_code VARCHAR(10) NOT NULL DEFAULT '0000',
    village_code VARCHAR(10) NOT NULL DEFAULT '0000',
    lgd_code VARCHAR(10) NOT NULL DEFAULT '0000',
    gender CHAR(1) NOT NULL CHECK (gender IN ('M', 'F', 'T')),
    age INT NOT NULL CHECK (age > 0),
    marital_status CHAR(1) NOT NULL CHECK (marital_status IN ('S', 'M', 'D', 'W')),
    annual_income NUMERIC NOT NULL CHECK (annual_income >= 0),
    educational_background INT NOT NULL CHECK (educational_background BETWEEN 1 AND 6),
    professional_background CHAR(1) NOT NULL CHECK (professional_background IN ('S', 'E', 'I', 'N')),
    application_start_timestamp TIMESTAMP NOT NULL,
    loan_sanction_timestamp TIMESTAMP NOT NULL,
    device_type CHAR(1) NOT NULL CHECK (device_type IN ('M', 'D')),
    active_status CHAR(1) NOT NULL DEFAULT 'Y',
    services_used TEXT[],  -- Ensure this is an array type
    reason_for_withdrawal VARCHAR(255) NOT NULL DEFAULT '0000',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (client_id, loan_id)
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
    loan_id, client_id, lender_name, loan_type, loan_product_name, sanctioned_amount, loan_channel, district, state, branch_code,
    pincode, ifsc_code, state_code, district_code, sub_district_code, village_code, lgd_code, gender, age,
    marital_status, annual_income, educational_background, professional_background, application_start_timestamp,
    loan_sanction_timestamp, device_type, active_status, services_used,
    reason_for_withdrawal
)
VALUES
(
    hash_sha256('L-102'),          -- Hash the loan_id
    hash_sha256('H-82731'),        -- Hash the client_id
    'ICICI Bank',
    2,
    'Home Loan',
    500000,
    '2',
    'Bangalore',
    'Karnataka',
    'BR002',
    '560001',
    'ICIC0001234',
    '27',
    '4002',
    '0000',
    '0000',
    '0000',
    'F',
    28,
    'M',
    600000,
    3,
    'E',
    '2024-07-15 10:00:00',
    '2024-07-15 14:00:00',
    'D',
    'Y',
    ARRAY['Home Application', 'Home Sanction'],
    'GOOD CREDIT SCORE'
),
(
    hash_sha256('L-103'),
    hash_sha256('H-82732'),
    'Axis Bank',
    3,
    'Car Loan',
    300000,
    '1',
    'Hyderabad',
    'Telangana',
    'BR003',
    '500001',
    'AXIS0001234',
    '29',
    '4003',
    '0000',
    '0000',
    '0000',
    'M',
    32,
    'S',
    450000,
    2,
    'S',
    '2024-08-20 11:30:00',
    '2024-08-20 15:00:00',
    'M',
    'Y',
    ARRAY['Car Application', 'Car Sanction', 'Car Disbursement'],
    'AVERAGE CREDIT SCORE'
),
(
    hash_sha256('L-104'),
    hash_sha256('H-82733'),
    'SBI',
    1,
    'Education Loan',
    150000,
    '3',
    'Chennai',
    'Tamil Nadu',
    'BR004',
    '600001',
    'SBI0001234',
    '30',
    '4004',
    '0000',
    '0000',
    '0000',
    'F',
    20,
    'W',
    300000,
    1,
    'E',
    '2024-05-10 09:00:00',
    '2024-05-10 13:00:00',
    'D',
    'Y',
    ARRAY['Education Application', 'Education Sanction'],
    'HIGH STUDENT DEBT'
),
(
    hash_sha256('L-105'),
    hash_sha256('H-82734'),
    'HDFC',
    2,
    'Personal Loan',
    200000,
    '2',
    'Ahmedabad',
    'Gujarat',
    'BR005',
    '380001',
    'HDFC0001234',
    '31',
    '4005',
    '0000',
    '0000',
    '0000',
    'M',
    40,
    'M',
    800000,
    5,
    'I',
    '2024-09-25 10:00:00',
    '2024-09-25 14:00:00',
    'M',
    'Y',
    ARRAY['Personal Application', 'Personal Sanction', 'Personal Disbursement'],
    'EXCELLENT CREDIT SCORE'
),
(
    hash_sha256('L-106'),
    hash_sha256('H-82735'),
    'PNB',
    3,
    'Business Loan',
    1000000,
    '3',
    'Kolkata',
    'West Bengal',
    'BR006',
    '700001',
    'PNB0001234',
    '32',
    '4006',
    '0000',
    '0000',
    '0000',
    'F',
    45,
    'M',
    1200000,
    6,
    'S',
    '2024-06-30 08:00:00',
    '2024-06-30 12:00:00',
    'D',
    'Y',
    ARRAY['Business Application', 'Business Sanction'],
    'GROWING BUSINESS'
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


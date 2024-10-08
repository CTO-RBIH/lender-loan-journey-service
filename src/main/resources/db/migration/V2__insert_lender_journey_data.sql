CREATE TABLE lender_loan_record (
    loan_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
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
    loan_disbursed_timestamp TIMESTAMP,
    device_type CHAR(1) NOT NULL CHECK (device_type IN ('M', 'D')),
    active_status CHAR(1) NOT NULL DEFAULT 'Y',
    services_used TEXT[],
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);


INSERT INTO lender_loan_record (
    lender_name, loan_type, loan_product_name, sanctioned_amount, loan_channel, district, state, branch_code,
    pincode, ifsc_code, state_code, district_code, sub_district_code, village_code, lgd_code, gender, age,
    marital_status, annual_income, educational_background, professional_background, application_start_timestamp,
    loan_sanction_timestamp, loan_disbursed_timestamp, device_type, active_status, services_used
)
VALUES (
    'HDFC', 1, 'Agriculture Loan', 250000, '1', 'Mumbai', 'Maharashtra', 'BR001', '400001', 'HDFC0001234',
    '27', '4001', '0000', '0000', '0000', 'M', 35, 'M', 4.5, 3, 'E', '2024-06-20 09:00:00',
    '2024-06-20 12:00:00', '2024-06-20 15:00:00', 'M', 'Y', '{"Loan Application", "Loan Sanction", "Loan Disbursement"}'
);

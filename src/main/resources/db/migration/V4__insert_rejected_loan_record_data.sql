INSERT INTO rejected_loan_record (
    loan_type,
    lender_name,
    district,
    state,
    branch_code,
    pincode,
    ifsc_code,
    gender,
    amount,
    application_start_timestamp,
    rejection_timestamp,
    product_name,
    age,
    loan_channel,
    reason_for_rejection
)
VALUES (
    'Personal Loan',
    'XYZ Bank',
    'Mumbai',
    'Maharashtra',
    'XYZ001',
    '400001',
    'XYZ0001',
    'M',
    100000.50,
    '2024-10-05 12:30:00',
    '2024-10-06 15:30:00',
    'Loan Product',
    30,
    'Online',
    'Low credit score'
);

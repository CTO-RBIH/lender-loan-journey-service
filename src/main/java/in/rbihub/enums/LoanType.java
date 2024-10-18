package in.rbihub.enums;

public enum LoanType {
    KCC_LOAN(1, "KCC Loan"),
    MSME_LOAN(2, "MSME Loan"),
    NON_KCC_AGRI_LOAN(3, "Non-KCC Agri Loan"),
    PERSONAL_LOAN(4, "Personal Loan"),
    HOUSING_LOAN(5, "Housing Loan"),
    EDUCATION_LOAN(6, "Education Loan"),
    VEHICLE_LOAN(7, "Vehicle Loan"),
    CATTLE_LOAN(8, "Cattle Loan"),
    GOLD_LOAN(9, "Gold Loan"),
    PENSION_LOAN(10, "Pension Loan");

    private final int id;
    private final String description;

    LoanType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static LoanType fromId(int id) {
        for (LoanType type : LoanType.values()) {
            if (type.id == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid loan type ID: " + id);
    }
}

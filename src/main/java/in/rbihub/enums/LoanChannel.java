package in.rbihub.enums;

public enum LoanChannel {
    SELF_SERVICE(1, "Self Service"),
    BRANCH_ASSISTED(2, "Branch Assisted"),
    FINTECH_ASSISTED(3, "BC or Fintech Assisted");

    private final int id;
    private final String description;

    LoanChannel(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static LoanChannel fromId(int id) {
        for (LoanChannel channel : values()) {
            if (channel.id == id) return channel;
        }
        throw new IllegalArgumentException("{loan_channel.invalid}");
    }
}

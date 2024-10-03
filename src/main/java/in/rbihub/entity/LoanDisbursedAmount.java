package in.rbihub.entity;

import lombok.Data;

@Data
public class LoanDisbursedAmount {
    private Double asOfLastWeek;
    private Double thisWeek;
    private Double cumulative;
}

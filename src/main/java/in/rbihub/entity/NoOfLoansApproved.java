package in.rbihub.entity;

import lombok.Data;

@Data
public class NoOfLoansApproved {
    private Long asOfLastWeek;
    private Long thisWeek;
    private Long cumulative;
}

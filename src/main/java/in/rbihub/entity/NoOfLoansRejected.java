package in.rbihub.entity;

import lombok.Data;

@Data
public class NoOfLoansRejected {
    private Long asOfLastWeek;
    private Long thisWeek;
    private Long cumulative;
    private Long businessDeclinePercentage;
    private Long technicalDeclinePercentage;
}

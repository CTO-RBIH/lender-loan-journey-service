package in.rbihub.entity;

import lombok.Data;

@Data
public class TotalApplicationsReceived {
    private Long asOfLastWeek;
    private Long thisWeek;
    private Long cumulative;
}

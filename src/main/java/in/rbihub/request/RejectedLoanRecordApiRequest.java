package in.rbihub.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RejectedLoanRecordApiRequest {

    private Meta meta;
    private RejectedLoanRecordBody data;
    private String hmac;

    @Data
    public static class Meta {
        private String txncode;
        private String ver;
        private String ts;
    }


}

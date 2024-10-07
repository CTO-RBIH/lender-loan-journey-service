package in.rbihub.utils;

import in.rbihub.request.RejectedLoanRecordApiRequest;
import in.rbihub.request.RejectedLoanRecordBody;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class RejectedLoanJourneyUtils {

    // Method to prepare the API request object
    public RejectedLoanRecordApiRequest prepareRejectedLoanRecordApiRequest(Map<String, String> headers, RejectedLoanRecordBody body) {
        RejectedLoanRecordApiRequest apiRequest = new RejectedLoanRecordApiRequest();
        apiRequest.setData(body);

        RejectedLoanRecordApiRequest.Meta meta = new RejectedLoanRecordApiRequest.Meta();
        meta.setTxncode(headers.getOrDefault("txncode", "defaultTxncode"));
        meta.setVer(headers.getOrDefault("version", "1.0"));
        meta.setTs(headers.getOrDefault("ts", LocalDateTime.now().toString())); // Set a default timestamp if missing

        apiRequest.setMeta(meta);
        apiRequest.setHmac(headers.get("hmac"));

        return apiRequest;
    }

}

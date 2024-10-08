package in.rbihub.utils;

import in.rbihub.common.utils.ApiUtil;
import in.rbihub.request.RejectedLoanRecordApiRequest;
import in.rbihub.request.RejectedLoanRecordBody;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static in.rbihub.common.utils.PlatformConstants.TIMESTAMP;
import static in.rbihub.common.utils.PlatformConstants.TXNCODE;

@Component
public class RejectedLoanJourneyUtils {

    @Autowired
    private ApiUtil apiUtil;

    @Value("${app.narrowVersions}")
    private List<String> narrowVersions;

    // Method to prepare the API request object
    public RejectedLoanRecordApiRequest prepareRejectedLoanRecordApiRequest(Map<String, String> headers, RejectedLoanRecordBody body) {
        RejectedLoanRecordApiRequest apiRequest = new RejectedLoanRecordApiRequest();

        // Use ApiUtil to prepare the API request headers
        apiUtil.prepareRequest(headers, apiRequest);

        // Set metadata (txncode, timestamp) from the body, if available
        if (Objects.nonNull(body.getMeta())) {
            apiRequest.setTxncode(body.getMeta().txncode());
            apiRequest.setTimestamp(body.getMeta().ts());

            // Set MDC for logging
            MDC.put(TXNCODE, body.getMeta().txncode());
            MDC.put(TIMESTAMP, body.getMeta().ts());
        }

        // Set the body in the API request
        apiRequest.setBody(body);

        // Set creation timestamp for the data entity, if present
        if (Objects.nonNull(body.getData())) {
            apiRequest.getBody().getData().setCreatedAt(Timestamp.from(Instant.now()));
        }

        return apiRequest;
    }
}

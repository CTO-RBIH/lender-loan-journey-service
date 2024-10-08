package in.rbihub.controller;

import in.rbihub.request.RejectedLoanRecordApiRequest;
import in.rbihub.request.RejectedLoanRecordBody;
import in.rbihub.service.RejectedLoanRecordService;
import in.rbihub.utils.RejectedLoanJourneyUtils;
import in.rbihub.common.utils.ApiUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
public class RejectedLoanRecordController {

    @Autowired
    private RejectedLoanRecordService rejectedLoanRecordService;

    @Autowired
    private RejectedLoanJourneyUtils rejectedLoanJourneyUtils;

    @Autowired
    private ApiUtil apiUtil;

    @PostMapping(path = "/rejected-loan-record/{version}/{lang}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createRejectedLoanRecord(@PathVariable("version") String version,
                                                           @PathVariable("lang") String lang,
                                                           @RequestHeader(value = "api-key", required = false) String apiKey,
                                                           @RequestHeader("client-id") String clientId,
                                                           @RequestHeader(value = "activityid", required = false) String correlationId,
                                                           @RequestHeader(value = "x-performance-test", required = false) Boolean doPerformanceTest,
                                                           @RequestBody RejectedLoanRecordBody body,
                                                           HttpServletRequest request) throws Exception {

        // Collect headers
        Map<String, String> headers = apiUtil.collectHeaders(request);
        headers.put("lang", lang);
        headers.put("version", version);

        log.info("Creating Rejected Loan Record, headers: {}, body: {}", headers, body);

        // Prepare the API request
        RejectedLoanRecordApiRequest apiRequest = rejectedLoanJourneyUtils.prepareRejectedLoanRecordApiRequest(headers, body);

        // Call service to handle rejected loan record
        String response = rejectedLoanRecordService.handleRejectedLoanRecord(apiRequest);

        // Return the response from the service
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

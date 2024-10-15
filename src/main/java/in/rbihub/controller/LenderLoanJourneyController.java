package in.rbihub.controller;

import in.rbihub.common.utils.ApiUtil;
import in.rbihub.entity.LenderLoanRecordId;
import in.rbihub.error.LenderLoanJourneyException;
import in.rbihub.request.LenderLoanRecordApiRequest;
import in.rbihub.request.LenderLoanRecordBody;
import in.rbihub.request.LenderLoanRecordPatchBody;
import in.rbihub.request.LenderLoanRecordUpdateRequest;
import in.rbihub.service.LenderLoanJourneyService;
import in.rbihub.utils.LenderLoanJourneyUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Slf4j
@RestController
public class LenderLoanJourneyController {
    @Autowired
    LenderLoanJourneyService lenderLoanJourneyService;
    @Autowired
    LenderLoanJourneyUtils lenderLoanJourneyUtils;
    @Autowired
    ApiUtil apiUtil;

    @PostMapping(path = "/lender-loan-record/{version}/{lang}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String create(@PathVariable("version") String version, @PathVariable("lang") String lang,
                         @RequestHeader(value = "api-key", required = false) String apiKey,
                         @RequestHeader(value = "client-id", required = true) String clientId,  // Extract client-id from header
                         @RequestHeader(value = "activityid", required = false) String correlationId,
                         @RequestHeader(value = "x-performance-test", required = false) Boolean doPerformanceTest,
                         @RequestBody(required = true) LenderLoanRecordBody body,
                         HttpServletRequest request) throws LenderLoanJourneyException {
        Map<String, String> headers = apiUtil.collectHeaders(request);
        headers.put("lang", lang);
        headers.put("version", version);
        headers.put("client-id", clientId);
        log.info("create. , headers: {}", headers);
        LenderLoanRecordApiRequest apiRequest = lenderLoanJourneyUtils.prepareLenderLoanRecordApiRequest(headers, body);



        return lenderLoanJourneyService.handleLenderLoanRecord(apiRequest);
    }

    @PostMapping(path = "/withdraw-loan-record/{version}/{lang}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String updateLenderLoanRecord(@PathVariable("version") String version,
                                         @PathVariable("lang") String lang,
                                         @RequestHeader(value = "client-id", required = true) String clientId,  // Extract client-id from header
                                         @RequestBody LenderLoanRecordPatchBody patchBody,  // Request body containing fields to update
                                         HttpServletRequest request) throws LenderLoanJourneyException {
        // Collect headers
        Map<String, String> headers = apiUtil.collectHeaders(request);
        headers.put("lang", lang);
        headers.put("version", version);
        headers.put("client-id", clientId);
        log.info("updateLenderLoanRecord, headers: {}", headers);

        // Hash the clientId
        LenderLoanRecordUpdateRequest apiRequest = lenderLoanJourneyUtils.getLenderLoanRecordUpdateRequest(headers, patchBody);

        // Pass the request to the service layer to handle the update
        return lenderLoanJourneyService.updateLoanWithdrawal(apiRequest);
    }
}

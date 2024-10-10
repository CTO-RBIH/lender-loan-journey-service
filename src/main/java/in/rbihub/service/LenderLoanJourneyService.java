package in.rbihub.service;

import in.rbihub.common.error.ApiParamException;
import in.rbihub.common.utils.ApiUtil;
import in.rbihub.common.validation.ApiValidator;
import in.rbihub.entity.LenderLoanRecordEntity;
import in.rbihub.error.LenderLoanJourneyException;
import in.rbihub.repository.LenderLoanRecordRepository;
import in.rbihub.request.LenderLoanRecordApiRequest;
import in.rbihub.utils.LenderLoanJourneyUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class LenderLoanJourneyService {

    @Autowired
    LenderLoanJourneyUtils lenderLoanJourneyUtils;

    @Autowired
    LenderLoanRecordRepository lenderLoanRecordRepository;

    @Autowired
    ApiValidator apiValidator;

    @Autowired
    ApiUtil apiUtil;

    public String handleLenderLoanRecord(LenderLoanRecordApiRequest apiRequest) throws LenderLoanJourneyException {

        log.info("Processing lender loan record: {}", apiRequest);

        // Validate the request body and the request itself
        apiValidator.validate(apiRequest.getBody());
        apiValidator.validate(apiRequest);

        // Retrieve the loan record from the request
        LenderLoanRecordEntity loanRecord = apiRequest.getBody().getData();

        // Save the loan record to the repository
        lenderLoanRecordRepository.save(loanRecord);

        // Create a valid JSON object to pass as data
        JSONObject jsonData = new JSONObject();
        jsonData.put("message", "Loan record processed successfully.");

        // Return the default success response
        return apiUtil.convertToPlatformResponse(apiRequest, jsonData);
    }

    private void validatePresence(String name, String metadataValues, ApiParamException.ErrorCodes errorCode) throws LenderLoanJourneyException {
        List<String> names = Arrays.asList(metadataValues.split(","));
        if (!names.contains(name)) {
            throw new LenderLoanJourneyException(errorCode);
        }
    }
}

package in.rbihub.service;

import in.rbihub.common.error.ApiParamException;
import in.rbihub.common.utils.ApiUtil;
import in.rbihub.common.validation.ApiValidator;
import in.rbihub.entity.LenderLoanRecordEntity;
import in.rbihub.entity.LenderLoanRecordId;
import in.rbihub.error.LenderLoanJourneyException;
import in.rbihub.repository.LenderLoanRecordRepository;
import in.rbihub.request.LenderLoanRecordApiRequest;
import in.rbihub.request.LenderLoanRecordUpdateRequest;
import in.rbihub.utils.LenderLoanJourneyUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

        // Create a new LenderLoanRecordId instance with original values
        LenderLoanRecordId recordId = new LenderLoanRecordId(loanRecord.getLoanId(), loanRecord.getClientId());

        // Set the hashed IDs in the loanRecord entity
        loanRecord.setHashedId(recordId.getLoanId(), recordId.getClientId());

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


    public void updateLoanWithdrawal(LenderLoanRecordUpdateRequest apiRequest) throws LenderLoanJourneyException {
        String clientId = apiRequest.getClientId();

        String loanId = apiRequest.getBody().getData().getLoanId();
        String reasonForWithdrawal = apiRequest.getBody().getData().getReason_for_rejection();

        if (clientId == null || loanId == null || reasonForWithdrawal == null) {
            throw new LenderLoanJourneyException("Missing required fields: client_id, loan_id, or reason_for_withdrawal.");
        }

        // Fetch the existing loan record
        LenderLoanRecordEntity loanRecord = lenderLoanRecordRepository.findByLoanIdAndClientId(loanId, clientId)
                .orElseThrow(() -> new LenderLoanJourneyException("Loan record not found for client_id: " + clientId + " and loan_id: " + loanId));

        // Only update the fields that are provided in the request
        loanRecord.setReasonForWithdrawal(reasonForWithdrawal);

        // Save the updated record (this acts as a PUT, but since we only updated one field, it mimics a PATCH)
        lenderLoanRecordRepository.save(loanRecord);

        JSONObject jsonData = new JSONObject();
        jsonData.put("message", "Loan record processed successfully.");

        // Return the default success response
        return apiUtil.convertToPlatformResponse(apiRequest, jsonData);
    }
}

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

    public String updateLoanWithdrawal(LenderLoanRecordUpdateRequest apiRequest) throws LenderLoanJourneyException {
        try {
            // Extracting data from the API request
            String clientId = apiRequest.getClientId();
            String loanId = apiRequest.getBody().getData().getLoanId();
            String reasonForWithdrawal = apiRequest.getBody().getData().getReason_for_rejection();

            // Check if any required field is missing
            if (clientId == null || loanId == null || reasonForWithdrawal == null) {
                throw new LenderLoanJourneyException("Missing required fields: client_id, loan_id, or reason_for_withdrawal.");
            }

            // Manually hash the clientId and loanId
            String hashedLoanId = LenderLoanRecordId.hash(loanId);
            String hashedClientId = LenderLoanRecordId.hash(clientId);

            log.info("Original loanId: {}, Original clientId: {}", loanId, clientId);
            log.info("Hashed loanId: {}, Hashed clientId: {}", hashedLoanId, hashedClientId);

            // Fetch the existing loan record using the hashed primary key combination
            LenderLoanRecordEntity loanRecord = lenderLoanRecordRepository.findByLoanIdAndClientId(hashedLoanId, hashedClientId)
                    .orElseThrow(() -> new LenderLoanJourneyException("Loan record not found for client_id: " + clientId + " and loan_id: " + loanId));

            // Update the fields that need to be changed
            loanRecord.setReasonForWithdrawal(reasonForWithdrawal);
            loanRecord.setActiveStatus("N"); // Change active status to 'N' (Inactive)

            // Save the updated loan record back to the repository
            lenderLoanRecordRepository.save(loanRecord);

            // Create response object
            JSONObject jsonData = new JSONObject();
            jsonData.put("message", "Loan record updated successfully.");

            // Return success response as a string
            return apiUtil.convertToPlatformResponse(apiRequest, jsonData);

        } catch (LenderLoanJourneyException e) {
            log.error("Error updating loan withdrawal: {}", e.getMessage());
            throw e;  // Re-throwing the custom exception for further handling
        } catch (Exception e) {
            log.error("Unexpected error occurred while updating loan withdrawal.", e);
            throw new LenderLoanJourneyException("An unexpected error occurred while processing the loan withdrawal update.", String.valueOf(e));
        }
    }

}

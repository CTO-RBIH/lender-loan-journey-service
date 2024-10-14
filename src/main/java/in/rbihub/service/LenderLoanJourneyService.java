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
import static in.rbihub.utils.LenderLoanJourneyUtils.hash;


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
        LenderLoanRecordId recordId = new LenderLoanRecordId(loanRecord.getLoanId(), loanRecord.getCustomerId());

        // Set the hashed IDs in the loanRecord entity
        loanRecord.setHashedId(recordId.getLoanId(), recordId.getCustomerId());

        // Save the loan record to the repository
        lenderLoanRecordRepository.save(loanRecord);

        // Create a valid JSON object to pass as data
        JSONObject jsonData = new JSONObject();
        jsonData.put("message", "Loan record processed successfully.");

        // Return the default success response
        return apiUtil.convertToPlatformResponse(apiRequest, jsonData);
    }

    public String updateLoanWithdrawal(LenderLoanRecordUpdateRequest apiRequest) throws LenderLoanJourneyException {
        // Extracting data from the API request
        String customerId = apiRequest.getBody().getData().getCustomerId();
        String loanId = apiRequest.getBody().getData().getLoanId();
        String reasonForWithdrawal = apiRequest.getBody().getData().getWithdrawalReason();

        // Check for missing required fields using patch_parameters_null.invalid
        if (customerId == null || customerId.isBlank() || loanId == null || loanId.isBlank() || reasonForWithdrawal == null || reasonForWithdrawal.isBlank()) {
            throw new LenderLoanJourneyException(LenderLoanJourneyException.CustomErrorCodes.E225,
                    "patch_parameters_null.invalid");
        }

        // Manually hash the customerId and loanId
        String hashedLoanId = hash(loanId);
        String hashedCustomerId = hash(customerId);

        // Fetch the existing loan record using the hashed primary key combination, use cust_loan_id_not_found.invalid if not found
        LenderLoanRecordEntity loanRecord = lenderLoanRecordRepository.findByLoanIdAndCustomerId(hashedLoanId, hashedCustomerId)
                .orElseThrow(() -> new LenderLoanJourneyException(LenderLoanJourneyException.CustomErrorCodes.E226,
                        "cust_loan_id_not_found.invalid"));

        // Update the fields that need to be changed
        loanRecord.setReasonForWithdrawal(reasonForWithdrawal);
        loanRecord.setActiveStatus("N"); // Change active status to 'N' (Inactive)

        // Save the updated loan record back to the repository
        lenderLoanRecordRepository.save(loanRecord);

        // Create a valid JSON object to pass as data
        JSONObject jsonData = new JSONObject();
        jsonData.put("message", "Loan record updated successfully.");

        // Return the default success response as a JSON object using apiUtil
        return apiUtil.convertToPlatformResponse(apiRequest, jsonData);
    }





}

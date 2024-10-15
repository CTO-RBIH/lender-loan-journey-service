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
import org.jetbrains.annotations.NotNull;
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

        // Extract clientId from headers
        String clientIdFromHeader = apiRequest.getClientId();

        // Validate the request body and the request itself
        apiValidator.validate(apiRequest.getBody());
        apiValidator.validate(apiRequest);

        // Retrieve the loan record from the request
        LenderLoanRecordEntity loanRecord = apiRequest.getBody().getData();

        // Concatenate loanId and clientId, hash it and set it in loanRecord
        loanRecord.setHashedId(loanRecord.getLoanId(), clientIdFromHeader);

        // Round and set the annual income before saving
        loanRecord.setAnnualIncome(loanRecord.getAnnualIncome());  // Rounding will be done inside setAnnualIncome

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
        String loanId = apiRequest.getBody().getData().getLoanId();
        Integer reasonForWithdrawal = apiRequest.getBody().getData().getWithdrawalReason();
        String concatenatedId = getString(apiRequest, loanId, reasonForWithdrawal);
        String hashedLoanId = hash(concatenatedId);  // Hash the concatenated loanId and clientId

        LenderLoanRecordEntity loanRecord = lenderLoanRecordRepository.findByLoanId(hashedLoanId)
                .orElseThrow(() -> new LenderLoanJourneyException(LenderLoanJourneyException.CustomErrorCodes.E226)); // Custom error code handled via message.properties

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

    @NotNull
    private static String getString(LenderLoanRecordUpdateRequest apiRequest, String loanId, Integer reasonForWithdrawal) throws LenderLoanJourneyException {
        String clientIdFromHeader = apiRequest.getClientId();

        // Check for missing required fields using patch_parameters_null.invalid
        if (loanId == null || loanId.isBlank()) {
            throw new LenderLoanJourneyException(LenderLoanJourneyException.CustomErrorCodes.E225,
                    "patch_parameters_null.invalid");
        }
        if (reasonForWithdrawal == null || reasonForWithdrawal == 0 || reasonForWithdrawal > 5 || reasonForWithdrawal < 1) {
            throw new LenderLoanJourneyException(LenderLoanJourneyException.CustomErrorCodes.E227,
                    "withdrawal_1_5.invalid");
        }

        // Concatenate loanId and clientId and then hash the result
        return loanId + "." + clientIdFromHeader;
    }


}

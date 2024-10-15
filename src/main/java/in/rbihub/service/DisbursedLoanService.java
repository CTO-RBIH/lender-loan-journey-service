package in.rbihub.service;

import in.rbihub.common.utils.ApiUtil;
import in.rbihub.common.validation.ApiValidator;
import in.rbihub.entity.DisbursedLoanEntity;
import in.rbihub.error.LenderLoanJourneyException;
import in.rbihub.repository.DisbursedLoanRecordRepository;
import in.rbihub.request.DisbursedLoanRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
public class DisbursedLoanService {

    @Autowired
    ApiValidator apiValidator;

    @Autowired
    DisbursedLoanRecordRepository disbursedLoanRepository;

    @Autowired
    ApiUtil apiUtil;

    public String handleDisbursedLoanRecord(DisbursedLoanRequest apiRequest) throws LenderLoanJourneyException {
        log.info("Processing disbursed loan record: {}", apiRequest);

        // Validate the request body and the request itself
        apiValidator.validate(apiRequest.getBody());
        apiValidator.validate(apiRequest);

        // Retrieve the disbursement loan record from the request
        DisbursedLoanEntity disbursedLoanRecord = apiRequest.getBody().getData();

        // Automatically set the tranche count for the same loan ID
        setTrancheCount(disbursedLoanRecord);

        // Perform validation that tranche count is not greater than total tranches
        validateTrancheCount(disbursedLoanRecord);

        // Perform any additional validation: e.g., Disbursed amount should not exceed the sanctioned amount
        validateAmountDisbursed(disbursedLoanRecord);

        // Save the disbursed loan record to the repository
        disbursedLoanRepository.save(disbursedLoanRecord);

        // Create a valid JSON object to pass as data
        JSONObject jsonData = new JSONObject();
        jsonData.put("message", "Disbursed loan record processed successfully.");

        // Return the default success response
        return apiUtil.convertToPlatformResponse(apiRequest, jsonData);
    }

    private void setTrancheCount(DisbursedLoanEntity disbursedLoanRecord) throws LenderLoanJourneyException {
        // Find the highest tranche count for the given loan_id
        Optional<DisbursedLoanEntity> latestDisbursement = disbursedLoanRepository.findTopByLoanIdOrderByTrancheCountDesc(disbursedLoanRecord.getLoanId());

        if (latestDisbursement.isPresent()) {
            int nextTrancheCount = latestDisbursement.get().getTrancheCount() + 1;
            disbursedLoanRecord.setTrancheCount(nextTrancheCount);
        } else {
            // Default to 1 if this is the first disbursement
            disbursedLoanRecord.setTrancheCount(1);
        }
    }

    private void validateTrancheCount(DisbursedLoanEntity disbursedLoanRecord) throws LenderLoanJourneyException {
        Integer totalTranches = disbursedLoanRecord.getTotalTranches();
        Integer trancheCount = disbursedLoanRecord.getTrancheCount();

        if (trancheCount > totalTranches) {
            throw new LenderLoanJourneyException(LenderLoanJourneyException.CustomErrorCodes.E230,
                    "tranche_count.invalid");
        }
    }

    private void validateAmountDisbursed(DisbursedLoanEntity disbursedLoanRecord) throws LenderLoanJourneyException {
        BigDecimal sanctionedAmount = disbursedLoanRecord.getSanctionedAmount();
        BigDecimal amountDisbursed = disbursedLoanRecord.getAmountDisbursed();

        // If sanctioned amount is provided, validate disbursed amount is less than or equal to sanctioned amount
        if (sanctionedAmount != null && amountDisbursed != null && amountDisbursed.compareTo(sanctionedAmount) > 0) {
            throw new LenderLoanJourneyException(LenderLoanJourneyException.CustomErrorCodes.E229,
                    "amount_disbursed_greater_than_sanctioned.invalid");
        }
    }
}

package in.rbihub.service;

import in.rbihub.common.error.ApiParamException;
import in.rbihub.common.utils.ApiUtil;
import in.rbihub.common.validation.ApiValidator;
import in.rbihub.entity.RejectedLoanRecordEntity;
import in.rbihub.repository.RejectedLoanRecordRepository;
import in.rbihub.request.RejectedLoanRecordApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class RejectedLoanRecordService {

    @Autowired
    private RejectedLoanRecordRepository rejectedLoanRecordRepository;

    @Autowired
    ApiValidator apiValidator;

    @Autowired
    ApiUtil apiUtil;

    // Method to handle rejected loan record
    public String handleRejectedLoanRecord(RejectedLoanRecordApiRequest apiRequest) throws ApiParamException {

        log.info("API Request Body: {}", apiRequest.getBody());

        // Validate the request body and the request itself
        apiValidator.validate(apiRequest.getBody());
        apiValidator.validate(apiRequest);

        // Extract data from the API request body
        RejectedLoanRecordEntity data = apiRequest.getBody().getData();

        // Ensure mandatory fields are not null (avoid null pointer issues)
        validatePresence(data.getLoanType(), "loanType", ApiParamException.ErrorCodes.E043);
        validatePresence(data.getLenderName(), "lenderName", ApiParamException.ErrorCodes.E043);

        // Convert the API request to the entity
        RejectedLoanRecordEntity rejectedLoanRecordEntity = new RejectedLoanRecordEntity();
        rejectedLoanRecordEntity.setLoanType(data.getLoanType());
        rejectedLoanRecordEntity.setLenderName(data.getLenderName());
        rejectedLoanRecordEntity.setDistrict(data.getDistrict());
        rejectedLoanRecordEntity.setState(data.getState());
        rejectedLoanRecordEntity.setBranchCode(data.getBranchCode());
        rejectedLoanRecordEntity.setPincode(data.getPincode());
        rejectedLoanRecordEntity.setIfscCode(data.getIfscCode());
        rejectedLoanRecordEntity.setGender(data.getGender());
        rejectedLoanRecordEntity.setAmount(data.getAmount());

        // Convert and set timestamps
        rejectedLoanRecordEntity.setApplicationStartTimestamp(data.getApplicationStartTimestamp());
        rejectedLoanRecordEntity.setRejectionTimestamp(data.getRejectionTimestamp());

        rejectedLoanRecordEntity.setProductName(data.getProductName());
        rejectedLoanRecordEntity.setAge(data.getAge());
        rejectedLoanRecordEntity.setLoanChannel(data.getLoanChannel());
        rejectedLoanRecordEntity.setReasonForRejection(data.getReasonForRejection());

        // Save the record to the database
        rejectedLoanRecordRepository.save(rejectedLoanRecordEntity);

        log.info("Rejected Loan Record saved successfully!");
        return ResponseEntity.status(HttpStatus.OK).body("OK").toString();
    }

    private void validatePresence(String value, String fieldName, ApiParamException.ErrorCodes errorCode) throws ApiParamException {
        if (value == null || value.isEmpty()) {
            log.error("Validation failed: Missing mandatory field {}", fieldName);
            throw new ApiParamException(errorCode);
        }
    }
}

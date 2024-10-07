package in.rbihub.service;

import in.rbihub.entity.RejectedLoanRecordEntity;
import in.rbihub.repository.RejectedLoanRecordRepository;
import in.rbihub.request.RejectedLoanRecordApiRequest;
import in.rbihub.request.RejectedLoanRecordBodyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RejectedLoanRecordService {

    @Autowired
    private RejectedLoanRecordRepository rejectedLoanRecordRepository;

    private static final Logger log = LoggerFactory.getLogger(RejectedLoanRecordService.class);

    // Method to convert string to Timestamp
    public Timestamp convertToTimestamp(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;  // Ensure null safety
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
        return Timestamp.valueOf(localDateTime);
    }

    // Method to handle rejected loan record
    public String handleRejectedLoanRecord(RejectedLoanRecordApiRequest apiRequest) {

        log.info("API Request Meta: {}, Data: {}", apiRequest.getMeta(), apiRequest.getData());

        // Extract data from the API request
        RejectedLoanRecordBodyData data = apiRequest.getData().getData();

        // Ensure mandatory fields are not null (avoid null pointer issues)
        if (data == null || data.getLoanType() == null || data.getLenderName() == null) {
            log.error("Invalid data: Missing loanType or lenderName.");
            return "Invalid data: Missing mandatory fields.";
        }

        // Convert the API request to the entity
        RejectedLoanRecordEntity rejectedLoanRecordEntity = new RejectedLoanRecordEntity();
        rejectedLoanRecordEntity.setLoanType(data.getLoanType());
        rejectedLoanRecordEntity.setLenderName(data.getLenderName());
        rejectedLoanRecordEntity.setDistrict(data.getDistrict());
        rejectedLoanRecordEntity.setState(data.getState());
        rejectedLoanRecordEntity.setBranchCode(data.getBranchCode());
        rejectedLoanRecordEntity.setPincode(data.getPinCode());
        rejectedLoanRecordEntity.setIfscCode(data.getIfscCode());
        rejectedLoanRecordEntity.setGender(data.getGender());
        rejectedLoanRecordEntity.setAmount(data.getLoanDisbursedAmount());

        // Convert and set timestamps
        rejectedLoanRecordEntity.setApplicationStartTimestamp(convertToTimestamp(data.getApplicationStartTimestamp()));
        rejectedLoanRecordEntity.setRejectionTimestamp(convertToTimestamp(data.getRejectionTimestamp()));

        rejectedLoanRecordEntity.setProductName(data.getProductName());
        rejectedLoanRecordEntity.setAge(data.getAge());
        rejectedLoanRecordEntity.setLoanChannel(data.getLoanChannel());
        rejectedLoanRecordEntity.setReasonForRejection(data.getReasonForRejection());

        // Save the record to the database
        rejectedLoanRecordRepository.save(rejectedLoanRecordEntity);

        log.info("Rejected Loan Record saved successfully!");
        return "Rejected Loan Record saved successfully!";
    }
}

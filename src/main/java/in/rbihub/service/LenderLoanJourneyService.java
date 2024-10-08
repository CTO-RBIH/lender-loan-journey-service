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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        apiValidator.validate(apiRequest.getBody());
        apiValidator.validate(apiRequest);
        LenderLoanRecordEntity loanRecord = apiRequest.getBody().getData();

//        validatePresence(loanRecord.getLenderName(), lenderNameMetadataValues, ApiParamException.ErrorCodes.E043);
//        validatePresence(loanRecord.getProductName(), productNameMetadataValues, ApiParamException.ErrorCodes.E017);

        lenderLoanRecordRepository.save(loanRecord);

        return ResponseEntity.status(HttpStatus.OK).body("OK").toString();
    }

    private void validatePresence(String name, String metadataValues, ApiParamException.ErrorCodes errorCode) throws LenderLoanJourneyException {
        List<String> names = Arrays.asList(metadataValues.split(","));
        if (!names.contains(name)) {
            throw new LenderLoanJourneyException(errorCode);
        }

    }
}

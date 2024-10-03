package in.rbihub.error;

import in.rbihub.common.error.ApiParamException.ErrorCodes;
import in.rbihub.common.markers.PlatfromErrorCode;
import in.rbihub.common.response.ApiResponse;
import in.rbihub.common.utils.ApiUtil;
import in.rbihub.error.LenderLoanJourneyException.CustomErrorCodes;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

import static in.rbihub.common.error.ApiParamException.ErrorCodes.E043;
import static in.rbihub.common.utils.PlatformConstants.TXNCODE;

@Slf4j
@ControllerAdvice
public class LenderLoanJourneyControllerAdvice {

	@Autowired
	ApiUtil apiUtil;
	@Autowired
	MessageSource ms;

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
		log.error("handleConstraintViolationException: {}", e.getMessage(), e);
		ConstraintViolation<?> violation = e.getConstraintViolations().stream().findFirst().get();
		String violationMsg = violation.getMessage();
		String message = violationMsg.split("#")[1];
		String errorCode = message.substring(0, 4);
		String errorMessage = message.substring(4).trim();
		LenderLoanJourneyException ex = new LenderLoanJourneyException(getPlatformErrorCode(errorCode), errorMessage);
		ApiResponse apiResponse = ApiResponse.builder().txncode(MDC.get(TXNCODE)).platformException(ex).build();
		String response = apiUtil.getPlatformApiResponse(apiResponse);
		log.info("handleConstraintViolationException. request processed.  Response: {}", response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler(LenderLoanJourneyException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	ResponseEntity<String> handleLenderLoanJourneyException(LenderLoanJourneyException e) {
		log.error("handleLenderLoanJourneyException: {}", e.getMessage(), e);
		PlatfromErrorCode platformErrorCode = getPlatformErrorCode(e.getErrorCode().name());
		if (e.getErrorCode().equals(E043)) {
			String message = getProviderMessageIfExists(e);
			e.setMessage(ms.getMessage(platformErrorCode.name(), null, Locale.ENGLISH) + message);
		} else {
			e.setMessage(ms.getMessage(platformErrorCode.name(), null, Locale.ENGLISH));
		}
		ApiResponse apiResponse = ApiResponse.builder().txncode(MDC.get(TXNCODE)).platformException(e).build();
		String response = apiUtil.getPlatformApiResponse(apiResponse);
		log.info("handleLenderLoanJourneyException. request processed.  Response: {}", response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * ErrorCodes: represents the common error codes. CustomErrorCodes: represents
	 * the provider specific error codes.
	 * 
	 * @param errorCode
	 * @return
	 */
	public PlatfromErrorCode getPlatformErrorCode(String errorCode) {
		if (EnumUtils.isValidEnum(ErrorCodes.class, errorCode)) {
			return ErrorCodes.valueOf(errorCode);
		} else {
			return CustomErrorCodes.valueOf(errorCode);
		}
	}

	private String getProviderMessageIfExists(LenderLoanJourneyException e) {
		if (Strings.isNotBlank(e.getMessage())) {
			return " : " + e.getMessage();
		}
		return "";
	}
}
package in.rbihub.error;

import in.rbihub.common.error.ApiParamException;
import in.rbihub.common.markers.PlatformException;
import in.rbihub.common.markers.PlatfromErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LenderLoanJourneyException extends ApiParamException implements PlatformException {

	private static final long serialVersionUID = 1L;
	private transient PlatfromErrorCode errorCode;
	private String message;
	private String providerErrCode;

	public LenderLoanJourneyException(PlatfromErrorCode errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}

	public LenderLoanJourneyException(PlatfromErrorCode errorCode, String message) {
		super(errorCode, message);
		this.errorCode = errorCode;
		this.message = message;
	}

	public LenderLoanJourneyException(String providerErrCode) {
		super(providerErrCode);
		this.providerErrCode = providerErrCode;
	}

	public LenderLoanJourneyException(String providerErrCode, String providerErrorMsg) {
		super(providerErrCode);
		this.providerErrCode = providerErrCode;
		this.message = providerErrorMsg;
	}

	public enum CustomErrorCodes implements PlatfromErrorCode {
		E201,
		E202,
		E203,
		E204,
		E205,
		E206,
		E207,
		E208,
		E209,
		E210,
		E211,
		E212,
		E213,
		E214,
		E215,
		E216,
		E217,
		E218,
		E219,
		E220,
		E221,
		E222,
		E223,
		E224,
		E225,
		E226
	}
}
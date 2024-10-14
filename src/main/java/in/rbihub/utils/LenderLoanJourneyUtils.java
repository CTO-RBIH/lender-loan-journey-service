package in.rbihub.utils;

import in.rbihub.common.utils.ApiUtil;
import in.rbihub.request.LenderLoanRecordApiRequest;
import in.rbihub.request.LenderLoanRecordBody;
import in.rbihub.request.LenderLoanRecordPatchBody;
import in.rbihub.request.LenderLoanRecordUpdateRequest;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static in.rbihub.common.utils.PlatformConstants.TIMESTAMP;
import static in.rbihub.common.utils.PlatformConstants.TXNCODE;

@Component
public class LenderLoanJourneyUtils {

	@Autowired
	ApiUtil apiUtil;

	@Value("${app.narrowVersions}")
	List<String> narrowVersions;

	public LenderLoanRecordApiRequest prepareLenderLoanRecordApiRequest(Map<String, String> headers, LenderLoanRecordBody body) {
		LenderLoanRecordApiRequest apiRequest = new LenderLoanRecordApiRequest();
		apiUtil.prepareRequest(headers, apiRequest);
		if (Objects.nonNull(body.getMeta())) {
			apiRequest.setTxncode(body.getMeta().txncode());
			apiRequest.setTimestamp(body.getMeta().ts());
			apiRequest.setVersion(body.getMeta().ver());
			MDC.put(TXNCODE, body.getMeta().txncode());
			MDC.put(TIMESTAMP, body.getMeta().ts());
		}

		apiRequest.setBody(body);
		apiRequest.getBody().getData().setCreatedAt(Timestamp.from(Instant.now()));
		/* provider fields */

		return apiRequest;
	}


	public LenderLoanRecordUpdateRequest getLenderLoanRecordUpdateRequest(Map<String, String> headers, LenderLoanRecordPatchBody body) {
		LenderLoanRecordUpdateRequest apiRequest = new LenderLoanRecordUpdateRequest();
		apiUtil.prepareRequest(headers, apiRequest);
		if (Objects.nonNull(body.getMeta())) {
			apiRequest.setTxncode(body.getMeta().txncode());
			apiRequest.setTimestamp(body.getMeta().ts());
			apiRequest.setVersion(body.getMeta().ver());
//			MDC.put(TXNCODE, body.getMeta().txncode());
//			MDC.put(TIMESTAMP, body.getMeta().ts());
		}

		apiRequest.setBody(body);

		return apiRequest;
	}

	// Hashing method using SHA-256
	public static String hash(String input) {
		if (input == null) {
			throw new IllegalArgumentException("Input to hash cannot be null");
		}
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = digest.digest(input.getBytes());
			StringBuilder hexString = new StringBuilder();
			for (byte b : hashedBytes) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error hashing input with SHA-256", e);
		}
	}
}

package in.rbihub.utils;

import in.rbihub.common.utils.ApiUtil;
import in.rbihub.entity.LenderLoanRecordEntity;
import in.rbihub.request.LenderLoanRecordApiRequest;
import in.rbihub.request.LenderLoanRecordBody;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
}
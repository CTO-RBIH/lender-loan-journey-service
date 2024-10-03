package in.rbihub.config;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import in.rbihub.common.markers.ApplicationConfig;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;

@ConfigurationProperties(prefix = "app")
@Configuration
@Data
@Validated
public class LenderLoanJourneyConfig implements ApplicationConfig {

	@Value("${app.secretkey}")
	private String secretkey;
	@Value("${app.privatekey}")
	private String privatekey;
	@Value("${app.sigpassword}")
	private String sigpassword;
	@Value("${app.keystore}")
	private String keystorepath;
	@Value("${app.keystorepass}")
	private String keystorepass;
	@Value("${app.keystorealias}")
	private String keystorealias;

	@Bean
	TimedAspect timedAspect(MeterRegistry registry) {
		return new TimedAspect(registry);
	}

	@Bean
	MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages","classpath:common_messages");
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		return messageSource;
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplateBuilder().build();
	}
	
}
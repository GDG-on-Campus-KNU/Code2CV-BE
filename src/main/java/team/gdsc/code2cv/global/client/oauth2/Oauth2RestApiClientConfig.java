package team.gdsc.code2cv.global.client.oauth2;

import java.time.Duration;

import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class Oauth2RestApiClientConfig {
	@Bean
	public Oauth2RestApiClient oauth2RestApiClient() {
		ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
			.withReadTimeout(Duration.ofSeconds(10));
		ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(settings);

		RestClient restClient = RestClient.builder()
			.baseUrl("")
			.defaultHeader("Accept", "application/json")
			.requestFactory(requestFactory)
			.defaultStatusHandler(HttpStatusCode::is5xxServerError,
				(request, response) -> {
					throw new RuntimeException("Oauth2 로그인 오류.");
				})
			.build();

		RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
		return factory.createClient(Oauth2RestApiClient.class);
	}
}

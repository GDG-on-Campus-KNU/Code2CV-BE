package team.gdsc.code2cv.global.client.github;

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

import team.gdsc.code2cv.core.exception.ExternalServerCommunicationException;

@Configuration
public class GithubRestApiClientConfig {
	private static final String GITHUB_API_BASE_URL = "https://api.github.com";
	private static final String GITHUB_ACCEPT_HEADER = "application/vnd.github+json";

	/**
	 * Github API를 호출하기 위한 RestClient 빈을 생성한다.
	 * 타임아웃 시간은 10초로 설정한다.
	 * @throws ExternalServerCommunicationException Github API 호출 중 에러가 발생했을 때 발생하는 예외
	 */
	@Bean
	public GithubRestApiClient githubRestApiClient() {
		ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
			.withReadTimeout(Duration.ofSeconds(10));
		ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(settings);

		RestClient restClient = RestClient.builder()
			.baseUrl(GITHUB_API_BASE_URL)
			.defaultHeader("Accept", GITHUB_ACCEPT_HEADER)
			.requestFactory(requestFactory)
			.defaultStatusHandler(HttpStatusCode::is5xxServerError,
				(request, response) -> {
					throw new ExternalServerCommunicationException("Github API 호출 중 에러가 발생했습니다.");
				})
			.build();
		RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
		return factory.createClient(GithubRestApiClient.class);
	}
}

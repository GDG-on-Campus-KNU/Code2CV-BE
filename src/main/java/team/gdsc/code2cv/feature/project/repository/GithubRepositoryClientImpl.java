package team.gdsc.code2cv.feature.project.repository;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import io.jsonwebtoken.JwtException;
import team.gdsc.code2cv.feature.project.dto.response.GithubCommitInfoModel;
import team.gdsc.code2cv.feature.project.dto.response.GithubRepositoryInfoModel;

/**
 * RestClient를 이용하여 Github API를 호출하는 클래스 <p>
 * 현재는 RestClient를 그대로 사용하고 있다. <br>
 * 향후 Http Interface를 이용해 추상화가 필요할 수도 있다.
 */
@Repository
public class GithubRepositoryClientImpl implements GithubRepositoryClient {
	private final RestClient restClient;

	/**
	 * RestClient를 생성한다.
	 * 타임아웃 시간은 10초로 설정한다.
	 * 4xx, 5xx 에러가 발생하면 예외를 던지도록 설정한다.
	 */
	public GithubRepositoryClientImpl() {
		String BASE_URL = "https://api.github.com";
		ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
			.withReadTimeout(Duration.ofSeconds(10));
		ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(settings);
		restClient = RestClient
			.builder()
			.baseUrl(BASE_URL)
			.requestFactory(requestFactory)
			.defaultStatusHandler(httpStatusCode -> httpStatusCode.is4xxClientError() || httpStatusCode.is5xxServerError(),
				(request, response) -> {
					// TODO 로그인한 사용자의 github token이 유효하지 않을 때, JwtException을 던지도록 수정
					if(false){
						throw new JwtException("JWT 토큰이 유효하지 않습니다.");
					}
					throw new RuntimeException("Github API 호출 중 에러가 발생했습니다.");
				})
			.build();
	}

	@Override
	public List<GithubRepositoryInfoModel> getRepositoryList(String token) {

		return restClient.get()
			.uri("/user/repos")
			.header("Authorization", "Bearer " + token)
			.retrieve()
			.body(new ParameterizedTypeReference<>() {
			});

	}

	@Override
	public List<GithubCommitInfoModel> getCommitList(String token, String owner, String repo) {

		return restClient.get()
			.uri("/repos/" + owner + "/" + repo + "/commits")
			.header("Authorization", "Bearer " + token)
			.retrieve()
			.body(new ParameterizedTypeReference<>() {
			});
	}
}

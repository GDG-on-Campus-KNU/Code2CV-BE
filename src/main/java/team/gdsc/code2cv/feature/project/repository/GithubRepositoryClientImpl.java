package team.gdsc.code2cv.feature.project.repository;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.project.dto.response.GithubCommitInfoModel;
import team.gdsc.code2cv.feature.project.dto.response.GithubRepositoryInfoModel;

/**
 * RestClient를 이용하여 Github API를 호출하는 클래스 <p>
 * 현재는 RestClient를 그대로 사용하고 있다. <br>
 * 향후 Http Interface를 이용해 추상화가 필요할 수도 있다.
 */
@Repository
@RequiredArgsConstructor
public class GithubRepositoryClientImpl implements GithubRepositoryClient {
	private final String BASE_URL = "https://api.github.com";

	@Override
	public List<GithubRepositoryInfoModel> getRepositoryList(String token) {
		RestClient restClient = RestClient.create();

		return restClient.get()
			.uri(BASE_URL + "/user/repos")
			.header("Authorization", "Bearer " + token)
			.retrieve()
			.onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), (request, response) -> {
				throw new RuntimeException("Github API 호출 중 에러가 발생했습니다.");
			})
			.body(new ParameterizedTypeReference<>() {});

	}

	@Override
	public List<GithubCommitInfoModel> getCommitList(String token, String owner, String repo) {
		RestClient restClient = RestClient.create();

		return restClient.get()
			.uri(BASE_URL + "/repos/" + owner + "/" + repo + "/commits")
			.header("Authorization", "Bearer " + token)
			.retrieve()
			.onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), (request, response) -> {
				throw new RuntimeException("Github API 호출 중 에러가 발생했습니다.");
			})
			.body(new ParameterizedTypeReference<>() {});
	}
}

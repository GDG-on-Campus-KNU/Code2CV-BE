package team.gdsc.code2cv.feature.project.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.global.client.github.response.GithubRepositoryInfoModel;
import team.gdsc.code2cv.global.client.github.GithubRestApiClient;

/**
 * Github API를 호출하여 GithubRepository 정보를 가져오는 클래스
 * Repository에 대한 정보를 가져오는 API 호출을 담당한다.
 */
@Repository
@RequiredArgsConstructor
public class GithubRepositoryClient {
	private final GithubRestApiClient githubRestApiClient;

	/**
	 * Github API [getRepositoryList]를 이용하여 사용자의 모든 Repository 정보를 가져온다.
	 * 다음 페이지가 없을 때까지 모든 페이지를 동기방식으로 가져와 List로 반환한다.
	 */
	public List<GithubRepositoryInfoModel> getAllRepositoriesInfo(String token) {
		return getRepositoriesInfoAllPageStream(token)
			.flatMap(List::stream)
			.toList();
	}

	/**
	 * Github API [getRepositoryList]를 이용하여 사용자의 모든 Repository 정보를 가져온다.
	 * 다음 페이지가 없을 때까지 모든 페이지를 가져오는 작업을 Stream으로 처리한다.
	 * 호출하는 곳에서는 Stream을 통해 데이터 처리를 향상 시킬 수 있다.
	 */
	public Stream<List<GithubRepositoryInfoModel>> getRepositoriesInfoAllPageStream(String token) {
		return Stream.iterate(1, page -> page + 1)
			.map(currentPage -> githubRestApiClient.getRepositoryList(
				"Bearer " + token, currentPage,
				30, "updated",
				null, null,
				"public", "owner,collaborator"
			))
			.map(HttpEntity::getBody)
			.takeWhile(response -> !response.isEmpty());
	}

}

package team.gdsc.code2cv.feature.projectAnalysis.repository.github;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.global.client.github.GithubRestApiClient;
import team.gdsc.code2cv.global.client.github.response.GithubCommitInfoModel;

@Repository
@RequiredArgsConstructor
public class GithubAnalysisClient {

	private final GithubRestApiClient githubRestApiClient;


	/*
	 * 프로젝트에서 작성된 커밋을 모두 가져온다. 최대 300개의 commit을 가져온다.
	 */
	public Stream<List<GithubCommitInfoModel>> getAllCommitSha(String token, String owner, String repo) {
		return Stream.iterate(1, page -> page + 1).limit(10)
			.map(currentPage -> githubRestApiClient.getCommits(
				"Bearer " + token, owner, repo, 30, currentPage
			))
			.map(HttpEntity::getBody)
			.takeWhile(response -> !response.isEmpty());
	}
}

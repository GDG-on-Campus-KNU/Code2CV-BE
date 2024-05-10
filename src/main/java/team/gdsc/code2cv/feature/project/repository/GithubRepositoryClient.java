package team.gdsc.code2cv.feature.project.repository;

import java.util.List;

import team.gdsc.code2cv.feature.project.dto.response.GithubCommitInfoModel;
import team.gdsc.code2cv.feature.project.dto.response.GithubRepositoryInfoModel;

public interface GithubRepositoryClient {
	List<GithubRepositoryInfoModel> getRepositoryList(String token);

	List<GithubCommitInfoModel> getCommitList(String token, String owner, String repo);
}

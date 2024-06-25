package team.gdsc.code2cv.feature.auth.repository;

import team.gdsc.code2cv.feature.user.domain.GithubAccount;

public interface GithubClient {
	String getGithubAccessToken(String code, String state);
	GithubAccount getGithubAccountResponse(String accessToken);
}

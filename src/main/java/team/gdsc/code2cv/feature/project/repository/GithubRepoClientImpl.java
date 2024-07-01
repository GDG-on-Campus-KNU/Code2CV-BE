package team.gdsc.code2cv.feature.project.repository;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class GithubRepoClientImpl implements GithubRepoClient{
	@Override
	public List<GithubRepoApiResponse> getAllRepositoriesInfo(String githubToken) {
		return null;
	}
}

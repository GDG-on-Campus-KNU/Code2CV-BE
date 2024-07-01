package team.gdsc.code2cv.feature.project.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.project.domain.ProjectCommand;
import team.gdsc.code2cv.feature.project.entity.Project;
import team.gdsc.code2cv.feature.project.repository.GithubRepoApiResponse;
import team.gdsc.code2cv.feature.project.repository.GithubRepoClient;
import team.gdsc.code2cv.feature.project.repository.ProjectRepository;
import team.gdsc.code2cv.feature.user.entity.User;
import team.gdsc.code2cv.feature.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {
	private final UserRepository userRepository;
	private final GithubRepoClient githubRepoClient;
	private final ProjectUpsertUseCase projectUpsertUseCase;

	/**
	 * 사용자의 Github Repository 정보를 가져와서 프로젝트로 변환하여 저장한다.
	 * 1. 사용자의 깃토큰으로 Github Repository 정보를 가져온다.
	 * 2. 가져온 정보를 프로젝트로 변환하여 저장하는 과정을 트랜잭션을 위해 [UseCase]에 위임한다.
	 */
	public void analyzeProject(Long userId) {
		User user = userRepository.findByIdOrThrow(userId);
		String githubToken = user.getGithubAccessToken();
		List<GithubRepoApiResponse> repositoriesInfo = githubRepoClient.getAllRepositoriesInfo(githubToken);
		projectUpsertUseCase.invoke(repositoriesInfo, userId);
	}
}


@Component
@RequiredArgsConstructor
class ProjectUpsertUseCase {
	private final ProjectRepository projectRepository;
	private final UserRepository userRepository;

	@Transactional
	public void invoke(List<GithubRepoApiResponse> repositoriesInfo, Long userId) {
		User user = userRepository.getReferenceById(userId);
		List<Project> projects = projectRepository.findAllByUserId(userId);
		// UPSERT TODO
	}
}
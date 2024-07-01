package team.gdsc.code2cv.feature.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.project.domain.ProjectCommand;
import team.gdsc.code2cv.feature.project.dto.ProjectRes;
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
	private final ProjectRepository projectRepository;
	private final ProjectUpsertUseCase projectUpsertUseCase;

	@Transactional(readOnly = true)
	public List<ProjectRes.ProjectDto> getAllProjects(Long userId) {
		List<Project> projects = projectRepository.findAllByUserId(userId);
		return projects.stream()
			// 수정일 최신순 정렬
			.sorted((p1, p2) -> p2.getRepoUpdatedAt().compareTo(p1.getRepoUpdatedAt()))
			.map(ProjectRes.ProjectDto::from)
			.toList();
	}

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

	/**
	 * Github Repository 정보를 프로젝트로 변환하여 저장한다.
	 * 1. 사용자의 프로젝트 목록을 조회한다.
	 * 2. 존재한다면 업데이트, 존재하지 않는다면 생성한다.
	 */
	@Transactional
	public void invoke(List<GithubRepoApiResponse> repositoriesInfo, Long userId) {
		User user = userRepository.getReferenceById(userId);
		List<Project> projects = projectRepository.findAllByUserId(userId);

		List<ProjectCommand.Create> createCommands = new ArrayList<>();
		Map<Project, ProjectCommand.Update> updateCommands = new HashMap<>();

		for (GithubRepoApiResponse repositoryInfo : repositoriesInfo) {
			Optional<Project> project = findProject(repositoryInfo, projects);
			project.ifPresentOrElse(
				p -> updateCommands.put(p, repositoryInfo.toUpdateCommand()),
				() -> createCommands.add(repositoryInfo.toCreateCommand())
			);
		}
		createCommands.stream()
			.map(c -> Project.create(c, user))
			.forEach(projectRepository::save);
		updateCommands.forEach(Project::update);
	}

	private Optional<Project> findProject(GithubRepoApiResponse repositoryInfo, List<Project> projects) {
		return projects.stream()
			.filter(p -> p.getRepoId().equals(repositoryInfo.id()))
			.findAny();
	}

}
package team.gdsc.code2cv.feature.projectAnalysis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.project.entity.Project;
import team.gdsc.code2cv.feature.project.repository.ProjectRepository;
import team.gdsc.code2cv.feature.projectAnalysis.repository.github.GithubAnalysisClient;
import team.gdsc.code2cv.feature.user.entity.User;
import team.gdsc.code2cv.feature.user.repository.UserRepository;
import team.gdsc.code2cv.global.client.github.response.GithubCommitInfoModel;

@Service
@RequiredArgsConstructor
public class ProjectAnalysisService {
	private final GithubAnalysisClient githubAnalysisClient;
	private final UserRepository userRepository;
	private final ProjectRepository projectRepository;

	/*
	 * 먼저 프로젝트에서 작성된 커밋을 모두 가져온다.
	 * 불러온 커밋 중 특정 유저가 작성한 커밋만 필터링한다.
	 * 해당 커밋에서 다시 한 번 sha를 통해 상세 정보 API를 요청한다. 이후 files의 변경 내역을 추출한다.
	 * 추출된 변경 내용을 List<String> 형태로 변환하기.
	 * 변환된 내용을 바탕으로 AI 분석 API를 호출하여 분석 결과를 받아온다.
	 */
	public void analyzeProject(Long projectId, Long userId) {
		//예외 처리 추가
		User user = userRepository.findById(userId).orElseThrow();
		Project project = projectRepository.findById(projectId).orElseThrow();
		List<String> commitsSha = getCommitsSha(user.getGithubAccessToken(), project.getOwner(), project.getRepo());
		// TODO: AI 분석 API 호출
		return;
	}

	private List<String> getCommitsSha(String token, String owner, String repo) {
		return githubAnalysisClient.getAllCommitSha(token, owner, repo)
			.flatMap(java.util.List::stream)
			.map(GithubCommitInfoModel::sha)
			.toList();
	}
}

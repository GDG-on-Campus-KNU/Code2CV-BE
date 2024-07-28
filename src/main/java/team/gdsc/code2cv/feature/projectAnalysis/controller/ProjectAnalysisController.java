package team.gdsc.code2cv.feature.projectAnalysis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.projectAnalysis.service.ProjectAnalysisService;
import team.gdsc.code2cv.global.jwt.JwtUser;

@Tag(name = "ProjectAnalysis", description = "프로젝트 분석 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project-analysis")
public class ProjectAnalysisController {

	private final ProjectAnalysisService projectAnalysisService;

	@Operation(summary = "프로젝트 분석", description = "사용자의 프로젝트를 분석하여 결과를 반환")
	@PostMapping("/analyze/{projectId}")
	public ResponseEntity<?> analyzeProject(
		@AuthenticationPrincipal JwtUser jwtUser,
		@PathVariable Long projectId
	) {
		projectAnalysisService.analyzeProject(projectId, jwtUser.getId());
		throw new UnsupportedOperationException("아직 구현되지 않았습니다.");
	}
}

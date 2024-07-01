package team.gdsc.code2cv.feature.project.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.project.service.ProjectService;
import team.gdsc.code2cv.global.jwt.JwtUser;

@Tag(name = "Project", description = "프로젝트 관련 API")
@RestController
@RequiredArgsConstructor
public class ProjectController {
	private final ProjectService projectService;


	@Operation(summary = "프로젝트 목록 조회", description = "사용자의 프로젝트 목록 전체 조회")
	@GetMapping("/api/projects")
	public List<Objects> getAllProjects() {
		throw new UnsupportedOperationException("아직 구현되지 않았습니다.");

	}

	@Operation(summary = "프로젝트 분석", description = "사용자 프로젝트를 갱신합니다.")
	@PostMapping("/api/projects/analyze")
	public void analyzeProject(@AuthenticationPrincipal JwtUser jwtUser) {
		projectService.analyzeProject(jwtUser.getId());
	}
}

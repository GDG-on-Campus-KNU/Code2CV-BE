package team.gdsc.code2cv.feature.resume.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.resume.dto.ResumeReq;
import team.gdsc.code2cv.feature.resume.dto.ResumeRes;
import team.gdsc.code2cv.feature.resume.service.ResumeService;
import team.gdsc.code2cv.global.jwt.JwtUser;

@Tag(name = "Resume", description = "이력서 API")
@RestController
@RequiredArgsConstructor
public class ResumeController {
	private final ResumeService resumeService;

	@GetMapping("/api/resumes")
	public List<ResumeRes.ResumeDto> getAllResumes(@AuthenticationPrincipal JwtUser jwtUser) {
		return resumeService.getAllResumes(jwtUser);
	}

	@GetMapping("/api/resume/{resumeId}")
	public ResumeRes.ResumeDetailDto getResume(@AuthenticationPrincipal JwtUser jwtUser, @PathVariable Long resumeId) {
		return resumeService.getResume(jwtUser, resumeId);
	}

	@PostMapping("/api/resume")
	public ResumeRes.ResumeDto createResume(@AuthenticationPrincipal JwtUser jwtUser, @RequestBody ResumeReq.CreateByNewRequest request) {
		return resumeService.createResume(jwtUser, request);
	}

	@PostMapping("/api/resume/upload")
	public ResumeRes.ResumeDto uploadResume(@AuthenticationPrincipal JwtUser jwtUser, @RequestBody ResumeReq.CreateByUploadRequest request) {
		return resumeService.uploadResume(jwtUser, request);
	}

	@PutMapping("/api/resume/{resumeId}")
	public ResumeRes.ResumeDto updateResume(@AuthenticationPrincipal JwtUser jwtUser, @PathVariable Long resumeId, @RequestBody ResumeReq.UpdateRequest request) {
		return resumeService.updateResume(jwtUser, resumeId, request);
	}
}

package team.gdsc.code2cv.feature.resume.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.resume.dto.ResumeRes;
import team.gdsc.code2cv.feature.resume.service.ResumeService;

@Tag(name = "Resume", description = "이력서 API")
@RestController
@RequiredArgsConstructor
public class ResumeController {
	private final ResumeService resumeService;

	@GetMapping("/api/resumes")
	public List<ResumeRes.ResumeDto> getResumes() {
		return resumeService.getAllResumes();
	}
}

package team.gdsc.code2cv.feature.resume.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.resume.dto.ResumeRes;
import team.gdsc.code2cv.feature.resume.entity.Resume;
import team.gdsc.code2cv.feature.resume.repository.ResumeRepository;
import team.gdsc.code2cv.global.jwt.JwtUser;

@Service
@RequiredArgsConstructor
public class ResumeService {
	private final ResumeRepository resumeRepository;

	@Transactional(readOnly = true)
	public List<ResumeRes.ResumeDto> getAllResumes(JwtUser jwtUser) {
		List<Resume> resumes = resumeRepository.findAllByUserId(jwtUser.getId());

		return resumes.stream()
			.map(ResumeRes.ResumeDto::from)
			.toList();
	}

	@Transactional(readOnly = true)
	public ResumeRes.ResumeDto getResume(JwtUser jwtUser, Long resumeId) {
		Resume resume = resumeRepository.findByIdAndUserId(resumeId, jwtUser.getId())
			.orElseThrow(() -> new IllegalArgumentException("해당 이력서를 찾을 수 없습니다."));

		return ResumeRes.ResumeDto.from(resume);
	}
}

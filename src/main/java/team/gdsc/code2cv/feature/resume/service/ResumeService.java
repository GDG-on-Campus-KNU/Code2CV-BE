package team.gdsc.code2cv.feature.resume.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.resume.domain.ResumeCommand;
import team.gdsc.code2cv.feature.resume.dto.ResumeReq;
import team.gdsc.code2cv.feature.resume.dto.ResumeRes;
import team.gdsc.code2cv.feature.resume.entity.Resume;
import team.gdsc.code2cv.feature.resume.repository.ResumeRepository;
import team.gdsc.code2cv.feature.user.entity.User;
import team.gdsc.code2cv.feature.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ResumeService {
	private final UserRepository userRepository;
	private final ResumeRepository resumeRepository;

	@Transactional(readOnly = true)
	public List<ResumeRes.ResumeDto> getAllResumes(Long userId) {
		// 유저 확인 후 존재하지 않으면 예외 발생
		User user = userRepository.findByIdOrThrow(userId);

		List<Resume> resumes = resumeRepository.findAllByUser(user);

		return resumes.stream()
			.map(ResumeRes.ResumeDto::from)
			.toList();
	}

	@Transactional(readOnly = true)
	public ResumeRes.ResumeDetailDto getResume(Long userId, Long resumeId) {
		// 유저 확인 후 존재하지 않으면 예외 발생
		User user = userRepository.findByIdOrThrow(userId);

		Resume resume = resumeRepository.findByIdAndUser(resumeId, user)
			.orElseThrow(() -> new IllegalArgumentException("해당 이력서를 찾을 수 없습니다."));

		return ResumeRes.ResumeDetailDto.from(resume);
	}

	public ResumeRes.ResumeDto createResume(Long userId, ResumeReq.CreateByNewRequest request) {
		// 유저 확인 후 존재하지 않으면 예외 발생
		User user = userRepository.findByIdOrThrow(userId);

		ResumeCommand.CreateByNew command = new ResumeCommand.CreateByNew(request.name().strip(), request.isDone());

		Resume resume = resumeRepository.save(Resume.create(command, user));

		return ResumeRes.ResumeDto.from(resume);
	}

	public ResumeRes.ResumeDto uploadResume(Long userId, ResumeReq.CreateByUploadRequest request) {
		// 유저 확인 후 존재하지 않으면 예외 발생
		User user = userRepository.findByIdOrThrow(userId);

		ResumeCommand.CreateByUpload command = new ResumeCommand.CreateByUpload(request.name().strip(),
			request.file().strip());

		Resume resume = resumeRepository.save(Resume.create(command, user));

		return ResumeRes.ResumeDto.from(resume);
	}

	public ResumeRes.ResumeDto updateResume(Long userId, Long resumeId, ResumeReq.UpdateRequest request) {
		// 유저 확인 후 존재하지 않으면 예외 발생
		User user = userRepository.findByIdOrThrow(userId);

		Resume resume = resumeRepository.findByIdAndUser(resumeId, user)
			.orElseThrow(() -> new IllegalArgumentException("해당 이력서를 찾을 수 없습니다."));

		resume.update(request);

		return ResumeRes.ResumeDto.from(resume);
	}
}

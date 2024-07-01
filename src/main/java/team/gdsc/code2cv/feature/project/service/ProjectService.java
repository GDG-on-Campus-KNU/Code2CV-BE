package team.gdsc.code2cv.feature.project.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.user.entity.User;
import team.gdsc.code2cv.feature.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {
	private final UserRepository userRepository;

	public void analyzeProject(Long userId) {
		User user = userRepository.findByIdOrThrow(userId);

	}
}

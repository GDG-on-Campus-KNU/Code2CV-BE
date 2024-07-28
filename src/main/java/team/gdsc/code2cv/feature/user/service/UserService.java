package team.gdsc.code2cv.feature.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.user.dto.UserRes;
import team.gdsc.code2cv.feature.user.entity.User;
import team.gdsc.code2cv.feature.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public UserRes.UserDto getUser(Long id) {
		User user = userRepository.findByIdOrThrow(id);
		return UserRes.UserDto.from(user);
	}
}

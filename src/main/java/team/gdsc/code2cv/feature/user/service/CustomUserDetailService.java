package team.gdsc.code2cv.feature.user.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.user.domain.UserCreate;
import team.gdsc.code2cv.feature.user.entity.User;
import team.gdsc.code2cv.feature.user.repository.UserRepository;
import team.gdsc.code2cv.global.jwt.JwtUser;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService {
	private final UserRepository userRepository;

}

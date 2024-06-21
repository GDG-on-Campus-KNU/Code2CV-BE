package team.gdsc.code2cv.feature.auth.service;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.gdsc.code2cv.feature.auth.dto.AuthReq;
import team.gdsc.code2cv.feature.auth.dto.AuthRes;
import team.gdsc.code2cv.feature.user.dto.UserRes;
import team.gdsc.code2cv.feature.user.entity.User;
import team.gdsc.code2cv.feature.user.repository.UserRepository;
import team.gdsc.code2cv.global.jwt.JwtProvider;
import team.gdsc.code2cv.global.jwt.JwtToken;
import team.gdsc.code2cv.global.jwt.JwtUser;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {
	private final JwtProvider jwtProvider;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public AuthRes.LoginResponse githubLoginOrSignUp(AuthReq.GithubLoginRequest req) {
		throw new NotImplementedException("아직 구현되지 않았습니다.");
	}

	public AuthRes.AccessTokenResponse refreshAccessToken(String rawToken) {
		String accessToken = jwtProvider.reissueAccessToken(rawToken);
		return AuthRes.AccessTokenResponse.of(accessToken);
	}

	public AuthRes.LoginResponse emailSignUp(AuthReq.EmailSignUpRequest req) {
		throw new NotImplementedException("아직 구현되지 않았습니다.");
	}

	@Transactional(readOnly = true)
	public AuthRes.LoginResponse emailLogin(AuthReq.EmailLoginRequest req) {
		User user = userRepository.findByEmail(req.email())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

		if (!bCryptPasswordEncoder.matches(req.password(), user.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
		JwtToken jwtToken = createToken(user);
		return AuthRes.LoginResponse.from(jwtToken, user);
	}

	private JwtToken createToken(User user) {
		JwtUser jwtUser = JwtUser.of(user.getId(), user.getRole());
		return jwtProvider.createToken(jwtUser);
	}

}

package team.gdsc.code2cv.feature.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.gdsc.code2cv.feature.auth.dto.AuthReq;
import team.gdsc.code2cv.feature.auth.dto.AuthRes;
import team.gdsc.code2cv.feature.auth.repository.GithubClient;
import team.gdsc.code2cv.feature.auth.repository.GithubRes;
import team.gdsc.code2cv.feature.user.domain.UserCommand;
import team.gdsc.code2cv.feature.user.domain.GithubAccount;
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
	private final GithubClient githubClient;

	/**
	 * 깃허브 로그인 또는 회원가입
	 * 1. 깃허브로부터 받은 code와 state를 이용하여 깃허브 access token을 받아온다.
	 * 2. 깃허브 access token을 이용하여 깃허브 계정 정보를 받아온다.
	 * 3. 깃허브 계정 정보를 이용하여 회원가입 또는 로그인을 진행한다.
	 */
	public AuthRes.LoginResponse githubLoginOrSignUp(AuthReq.GithubLoginRequest req) {
		String githubAccessToken
			= githubClient.getGithubAccessToken(req.code(), req.state());

		GithubAccount githubAccount = githubClient
			.getGithubAccountResponse(githubAccessToken)
			.toDomain(githubAccessToken);

		User user = userRepository.findByGithubAccountGithubId(githubAccount.getGithubId())
			.orElseGet(() -> signUp(githubAccount));

		JwtToken jwtToken = createToken(user);
		return AuthRes.LoginResponse.from(jwtToken, user);
	}

	/**
	 * 액세스 토큰 재발급
	 */
	public AuthRes.AccessTokenResponse refreshAccessToken(String rawToken) {
		jwtProvider.validateToken(rawToken, false);
		String accessToken = jwtProvider.reissueAccessToken(rawToken);
		return AuthRes.AccessTokenResponse.of(accessToken);
	}

	/**
	 * 이메일 회원가입
	 */
	public AuthRes.LoginResponse emailSignUp(AuthReq.EmailSignUpRequest req) {
		if (userRepository.existsByEmail(req.email())) {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		}
		GithubAccount githubAccount = githubClient
			.getGithubAccountResponse(req.gitToken())
			.toDomain(req.gitToken());

		UserCommand.CreateByEmail command = UserCommand.CreateByEmail.builder()
			.email(req.email())
			.password(bCryptPasswordEncoder.encode(req.password()))
			.githubAccount(githubAccount)
			.build();

		User user = User.create(command);

		userRepository.save(user);

		JwtToken jwtToken = createToken(user);
		return AuthRes.LoginResponse.from(jwtToken, user);
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

	private User signUp(GithubAccount githubAccount) {
		UserCommand.CreateByGithub command = UserCommand.CreateByGithub.builder()
			.githubAccount(githubAccount)
			.build();
		User user = User.create(command);
		return userRepository.save(user);
	}

}

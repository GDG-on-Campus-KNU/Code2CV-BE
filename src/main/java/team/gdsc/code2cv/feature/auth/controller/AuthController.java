package team.gdsc.code2cv.feature.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.auth.dto.AuthReq;
import team.gdsc.code2cv.feature.auth.dto.AuthRes;
import team.gdsc.code2cv.feature.auth.service.AuthService;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@Operation(summary = "깃허브 로그인", description = "깃허브 로그인 or 회원가입 / 성공시, JWT 토큰과 유저정보 반환")
	@PostMapping("/api/auth/oauth2/github")
	public AuthRes.LoginResponse githubLogin(@RequestBody AuthReq.GithubLoginRequest req) {
		return authService.githubLoginOrSignUp(req);
	}

	@Operation(summary = "액세스 토큰 재발급", description = "리프레시 토큰을 이용하여 액세스 토큰 재발급")
	@PostMapping("/api/auth/refresh")
	public AuthRes.AccessTokenResponse reissueAccessToken(@RequestHeader("Authorization") String authorization) {
		// Bearer 유효성 검사
		if (!authorization.startsWith("Bearer ")) {
			throw new IllegalArgumentException("Bearer 토큰이 아닙니다.");
		}
		String rawToken = authorization.substring(7);
		return authService.reissueAccessToken(rawToken);
	}

	@Operation(summary = "이메일 회원가입", description = "gitToken으로 유저정보 식별 / 회원가입 성공시, JWT 토큰과 유저정보 반환")
	@PostMapping("/api/auth/signup")
	public AuthRes.LoginResponse emailSignUp(@RequestBody AuthReq.EmailSignUpRequest req) {
		return authService.emailSignUp(req);
	}
	@Operation(summary = "이메일 로그인", description = "로그인 성공시, JWT 토큰과 유저정보 반환")
	@PostMapping("/api/auth/login")
	public AuthRes.LoginResponse emailLogin(@RequestBody AuthReq.EmailLoginRequest req) {
		return authService.emailLogin(req);
	}

}

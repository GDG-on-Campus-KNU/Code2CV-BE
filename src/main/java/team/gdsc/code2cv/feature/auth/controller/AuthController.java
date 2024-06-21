package team.gdsc.code2cv.feature.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@Operation(summary = "깃허브 로그인", description = "로그인 성공시, JWT 토큰과 유저정보 반환")
	@PostMapping("/api/login/oauth2/github")
	public AuthRes.LoginResponse githubLogin(@RequestBody AuthReq.GithubLoginRequest req) {
		return authService.githubLoginOrSignUp(req);
	}
}

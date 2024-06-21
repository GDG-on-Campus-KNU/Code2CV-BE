package team.gdsc.code2cv.feature.auth.service;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.gdsc.code2cv.feature.auth.dto.AuthReq;
import team.gdsc.code2cv.feature.auth.dto.AuthRes;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {
	public AuthRes.LoginResponse githubLoginOrSignUp(AuthReq.GithubLoginRequest req) {
		throw new NotImplementedException("아직 구현되지 않았습니다.");
	}

	public AuthRes.AccessTokenResponse refreshAccessToken(String rawToken) {
		throw new NotImplementedException("아직 구현되지 않았습니다.");
	}

	public AuthRes.LoginResponse emailSignUp(AuthReq.EmailSignUpRequest req) {
		throw new NotImplementedException("아직 구현되지 않았습니다.");
	}

	public AuthRes.LoginResponse emailLogin(AuthReq.EmailLoginRequest req) {
		throw new NotImplementedException("아직 구현되지 않았습니다.");
	}

}

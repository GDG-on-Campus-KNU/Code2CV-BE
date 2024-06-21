package team.gdsc.code2cv.feature.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.user.service.CustomUserDetailService;
import team.gdsc.code2cv.global.client.github.GithubRestApiClient;
import team.gdsc.code2cv.global.client.oauth2.Oauth2RestApiClient;
import team.gdsc.code2cv.global.client.oauth2.Oauth2UserModel;
import team.gdsc.code2cv.global.jwt.JwtProvider;
import team.gdsc.code2cv.global.jwt.JwtToken;

@RestController
@RequiredArgsConstructor
public class Oauth2LoginController {

	private final Oauth2RestApiClient oauth2RestApiClient;
	private final GithubRestApiClient githubRestApiClient;
	private final CustomUserDetailService customUserDetailService;
	private final JwtProvider jwtProvider;

	@PostMapping("/login/oauth2/github")
	public ResponseEntity<JwtToken> githubLogin(@RequestParam("code") String code) {
		String accessToken = oauth2RestApiClient.getGithubToken(code, "client_id",
				"client_secret", "http://localhost:8080/login/github")
			.getBody().accessToken();

		var user = githubRestApiClient.getUserInfo("Bearer " + accessToken).getBody();
		Oauth2UserModel oauth2UserModel = Oauth2UserModel.from(user);
		var body = jwtProvider.createToken(customUserDetailService.loadUserByUserToken(oauth2UserModel.userToken()));
		return ResponseEntity.status(HttpStatus.OK)
			.body(body);
	}

	//userDetails를 확인해서 로그인 성공 여부 확인
	@GetMapping("/loginSuccess/test")
	public ResponseEntity<String> loginSuccessTest(
		@AuthenticationPrincipal UserDetails userDetails) {
		System.out.println("userDetails:" + userDetails.toString());
		return ResponseEntity.status(HttpStatus.OK).body(userDetails.getAuthorities().toString());
	}
}

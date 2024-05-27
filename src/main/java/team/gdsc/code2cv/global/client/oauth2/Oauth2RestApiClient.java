package team.gdsc.code2cv.global.client.oauth2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import team.gdsc.code2cv.global.client.oauth2.response.Oauth2GithubTokenInfoModel;

@HttpExchange
public interface Oauth2RestApiClient {

	@GetExchange("https://github.com//login/oauth/access_token")
	ResponseEntity<Oauth2GithubTokenInfoModel> getGithubToken(
		@RequestParam(value = "code") String code,
		@RequestParam(value = "client_id") String clientId,
		@RequestParam(value = "client_secret") String clientSecret,
		@RequestParam(value = "redirect_uri") String redirectUri
	);
}

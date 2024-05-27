package team.gdsc.code2cv.global.client.oauth2.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Oauth2GithubTokenInfoModel(
	@JsonProperty("access_token") String accessToken,
	@JsonProperty("scope") String scope,
	@JsonProperty("token_type") String tokenType
) {
}
package team.gdsc.code2cv.feature.auth.dto;

import team.gdsc.code2cv.feature.user.dto.UserRes;

public class AuthRes {
	public record LoginResponse(
		String accessToken,
		String refreshToken,
		UserRes.UserDto user
	){}

	public record AccessTokenResponse(
		String accessToken
	){
		public static AccessTokenResponse of(String accessToken) {
			return new AccessTokenResponse(accessToken);
		}
	}
}

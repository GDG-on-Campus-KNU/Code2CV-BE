package team.gdsc.code2cv.feature.auth.dto;

import lombok.Builder;
import team.gdsc.code2cv.feature.user.dto.UserRes;
import team.gdsc.code2cv.feature.user.entity.User;
import team.gdsc.code2cv.global.jwt.JwtToken;

public class AuthRes {
	@Builder
	public record LoginResponse(
		String accessToken,
		String refreshToken,
		UserRes.UserDto user
	){
		public static LoginResponse from(JwtToken jwtToken, User user) {
			var userDto = UserRes.UserDto.from(user);
			return LoginResponse.builder()
				.accessToken(jwtToken.getAccessToken())
				.refreshToken(jwtToken.getRefreshToken())
				.user(userDto)
				.build();
		}
	}

	public record AccessTokenResponse(
		String accessToken
	){
		public static AccessTokenResponse of(String accessToken) {
			return new AccessTokenResponse(accessToken);
		}
	}
}

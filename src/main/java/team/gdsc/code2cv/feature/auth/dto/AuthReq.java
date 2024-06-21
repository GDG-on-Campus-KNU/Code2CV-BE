package team.gdsc.code2cv.feature.auth.dto;

import jakarta.validation.constraints.NotNull;

public class AuthReq {
	public record GithubLoginRequest(
		@NotNull(message = "code는 필수입니다.")
		String code,
		@NotNull(message = "state는 필수입니다.")
		String state
	){}
}

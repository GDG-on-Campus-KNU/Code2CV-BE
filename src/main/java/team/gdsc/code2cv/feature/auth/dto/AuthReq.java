package team.gdsc.code2cv.feature.auth.dto;

import jakarta.validation.constraints.NotNull;

public class AuthReq {
	public record GithubLoginRequest(
		@NotNull(message = "code는 필수입니다.")
		String code,
		@NotNull(message = "state는 필수입니다.")
		String state
	){}

	public record EmailSignUpRequest(
		@NotNull(message = "email은 필수입니다.")
		String email,
		@NotNull(message = "password는 필수입니다.")
		String password,
		@NotNull(message = "gitToken은 필수입니다.")
		String gitToken
	){}

	public record EmailLoginRequest(
		@NotNull(message = "email은 필수입니다.")
		String email,
		@NotNull(message = "password는 필수입니다.")
		String password
	){}
}

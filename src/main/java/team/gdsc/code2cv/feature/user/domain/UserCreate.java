package team.gdsc.code2cv.feature.user.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import team.gdsc.code2cv.core.utils.SelfValidating;

@Getter
@Builder
public class UserCreate extends SelfValidating<UserCreate> {
	/*
	로그인 로직과 깃허브 연동 로직 분리
	 */
	@NotNull
	private final String userToken;

	public UserCreate(String userToken) {
		this.userToken = userToken;
		this.validateSelf();
	}
}

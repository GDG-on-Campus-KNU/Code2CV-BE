package team.gdsc.code2cv.feature.user.domain;

import lombok.Builder;
import lombok.Getter;
import team.gdsc.code2cv.core.utils.SelfValidating;

@Getter
@Builder
public class UserAccountCreate extends SelfValidating<UserAccountCreate> {
	/*
	로그인 로직과 깃허브 연동 로직 분리
	 */
	@ValidUserToken
	private final String userToken;

	public UserAccountCreate(String userToken) {
		this.userToken = userToken;
		this.validateSelf();
	}
}

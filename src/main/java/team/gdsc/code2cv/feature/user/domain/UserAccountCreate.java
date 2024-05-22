package team.gdsc.code2cv.feature.user.domain;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import team.gdsc.code2cv.core.utils.SelfValidating;

@Getter
@Builder
public class UserAccountCreate extends SelfValidating<UserAccountCreate> {
	/*
	로그인 로직과 깃허브 연동 로직 분리
	 */
	@Size(min = 0, message = "아이디는 0자 이상이어야 합니다.")
	private final String vendorEmail;
	@ValidVendorName
	private final String vendorName;

	public UserAccountCreate(String vendorEmail, String vendorName) {
		this.vendorEmail = vendorEmail;
		this.vendorName = vendorName;
		this.validateSelf();
	}
}

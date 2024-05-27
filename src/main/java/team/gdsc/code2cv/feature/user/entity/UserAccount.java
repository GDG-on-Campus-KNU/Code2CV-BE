package team.gdsc.code2cv.feature.user.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gdsc.code2cv.feature.user.domain.UserAccountCreate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Enumerated(EnumType.STRING)
	private Role role;

	private String userToken;

	@Enumerated(EnumType.STRING)
	private VendorName vendorName;

	private String githubToken;

	private String githubNickname;

	@CreatedDate
	private LocalDateTime createdAt;

	@Builder
	private UserAccount(Role role, String userToken, VendorName vendorName, String githubToken,
		String githubNickname) {
		this.role = role;
		this.userToken = userToken;
		this.vendorName = vendorName;
		this.githubToken = githubToken;
		this.githubNickname = githubNickname;
	}

	public static UserAccount create(UserAccountCreate userAccountCreate) {
		return UserAccount.builder()
			.role(Role.ROLE_USER)
			.userToken(userAccountCreate.getUserToken())
			.build();
	}
}

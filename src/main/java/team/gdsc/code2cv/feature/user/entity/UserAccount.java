package team.gdsc.code2cv.feature.user.entity;

import java.time.LocalDateTime;

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

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Enumerated(EnumType.STRING)
	private UserRole role;

	private String vendorEmail;

	@Enumerated(EnumType.STRING)
	private VendorName vendorName;

	private String githubToken;

	private String githubNickname;

	private LocalDateTime createdAt; // JPA data type LocalDateTime 추가 필요

	@Builder
	public UserAccount(UserRole role, String vendorEmail, VendorName vendorName, String githubToken,
		String githubNickname, LocalDateTime createdAt) {
		this.role = role;
		this.vendorEmail = vendorEmail;
		this.vendorName = vendorName;
		this.githubToken = githubToken;
		this.githubNickname = githubNickname;
		this.createdAt = createdAt;
	}

	public static UserAccount create(UserRole role, String vendorEmail, VendorName vendorName, String githubToken,
		String githubNickname, LocalDateTime createdAt) {
		return UserAccount.builder()
			.role(role)
			.vendorEmail(vendorEmail)
			.vendorName(vendorName)
			.githubToken(githubToken)
			.githubNickname(githubNickname)
			.createdAt(createdAt)
			.build();
	}
}

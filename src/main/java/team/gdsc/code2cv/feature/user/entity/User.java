package team.gdsc.code2cv.feature.user.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gdsc.code2cv.feature.user.domain.UserCreate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users") // 예약어 회피
@AllArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Role role;

	private String userToken;

	@Enumerated(EnumType.STRING)
	private VendorName vendorName;

	private String githubToken;

	private String githubNickname;

	@CreatedDate
	private LocalDateTime createdAt;


	public static User create(UserCreate userCreate) {
		return User.builder()
			.role(Role.ROLE_USER)
			.userToken(userCreate.getUserToken())
			.build();
	}
}

package team.gdsc.code2cv.feature.user.entity;

import jakarta.persistence.Embedded;
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
import team.gdsc.code2cv.feature.user.domain.GithubAccount;
import team.gdsc.code2cv.feature.user.domain.Role;
import team.gdsc.code2cv.feature.user.domain.UserCommand;
import team.gdsc.code2cv.feature.user.domain.UserProfile;
import team.gdsc.code2cv.global.repository.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users") // 예약어 회피
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Role role;

	private String email; // 이메일 로그인 시 사용

	private String password; // 이메일 로그인 시 사용


	@Embedded
	private GithubAccount githubAccount;

	@Embedded
	private UserProfile userProfile;

	public String getGithubAccessToken() {
		return githubAccount.getGithubAccessToken();
	}


	public static User create(UserCommand.CreateByGithub command) {
		return User.builder()
			.role(Role.USER)
			.githubAccount(command.getGithubAccount())
			.build();
	}

	public static User create(UserCommand.CreateByEmail command) {
		return User.builder()
			.role(Role.USER)
			.email(command.getEmail())
			.password(command.getPassword())
			.githubAccount(command.getGithubAccount())
			.build();
	}


	public void updateGithubAccount(GithubAccount githubAccount) {
		this.githubAccount = githubAccount;
	}
}

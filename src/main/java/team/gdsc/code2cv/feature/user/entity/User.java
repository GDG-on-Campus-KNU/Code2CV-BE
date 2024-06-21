package team.gdsc.code2cv.feature.user.entity;

import java.time.LocalDateTime;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.annotation.CreatedDate;

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



	@CreatedDate
	private LocalDateTime createdAt;

	@Embedded
	private GithubAccount githubAccount;

	@Embedded
	private UserProfile userProfile;

	public String getGithubAccessToken() {
		return githubAccount.getGithubAccessToken();
	}


	public static User create(UserCreate userCreate) {
		throw new NotImplementedException("Not implemented yet");
	}
}

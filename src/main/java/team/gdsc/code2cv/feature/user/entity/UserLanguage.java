package team.gdsc.code2cv.feature.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class UserLanguage {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private LanguageType languageType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_account_id")
	private UserAccount userAccount;

	private Long rowsAdded;
	private Long rowsDeleted;
	private String commits;
	private Long experience;
	private LocalDateTime lastUpdate; // JPA data type LocalDateTime 추가 필요

	@Builder
	public UserLanguage(UserAccount userAccount, Long rowsAdded, Long rowsDeleted, String commits, Long experience,
		LocalDateTime lastUpdate) {
		this.userAccount = userAccount;
		this.rowsAdded = rowsAdded;
		this.rowsDeleted = rowsDeleted;
		this.commits = commits;
		this.experience = experience;
		this.lastUpdate = lastUpdate;
	}

	public static UserLanguage create(UserAccount userAccount, Long rowsAdded, Long rowsDeleted, String commits,
		Long experience, LocalDateTime lastUpdate) {
		return UserLanguage.builder()
			.userAccount(userAccount)
			.rowsAdded(rowsAdded)
			.rowsDeleted(rowsDeleted)
			.commits(commits)
			.experience(experience)
			.lastUpdate(lastUpdate)
			.build();
	}
}

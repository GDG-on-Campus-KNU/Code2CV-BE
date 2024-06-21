package team.gdsc.code2cv.feature.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gdsc.code2cv.feature.user.domain.UserLanguageCreate;

@Entity
@NoArgsConstructor
@Getter
public class UserLanguage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private LanguageType languageType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_account_id")
	private User user;
	private Long rowsAdded;
	private Long rowsDeleted;
	private String commits;
	private Long experience;

	private LocalDateTime lastUpdate;

	@Builder
	private UserLanguage(User user, LanguageType languageType, Long rowsAdded, Long rowsDeleted,
		String commits, Long experience, LocalDateTime lastUpdate) {
		this.user = user;
		this.languageType = languageType;
		this.rowsAdded = rowsAdded;
		this.rowsDeleted = rowsDeleted;
		this.commits = commits;
		this.experience = experience;
		this.lastUpdate = lastUpdate;
	}

	public static UserLanguage create(User user, UserLanguageCreate userLanguageCreate) {
		return UserLanguage.builder()
			.user(user)
			.languageType(userLanguageCreate.getLanguageType())
			.rowsAdded(userLanguageCreate.getRowsAdded())
			.rowsDeleted(userLanguageCreate.getRowsDeleted())
			.commits(userLanguageCreate.getCommits())
			.experience(userLanguageCreate.getExperience())
			.lastUpdate(LocalDateTime.now())
			.build();
	}
}

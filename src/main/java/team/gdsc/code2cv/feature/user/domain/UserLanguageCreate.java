package team.gdsc.code2cv.feature.user.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import team.gdsc.code2cv.core.utils.SelfValidating;
import team.gdsc.code2cv.feature.user.entity.LanguageType;

public class UserLanguageCreate extends SelfValidating<UserLanguageCreate> {

	private final LanguageType languageType; // Lang

	@Min(0)
	private final Long rowsAdded;
	@Min(0)
	private final Long rowsDeleted;
	@Size(min = 0)
	private final String commits;
	@Min(0)
	private final Long experience;

	public UserLanguageCreate(LanguageType languageType, Long rowsAdded, Long rowsDeleted, String commits,
		Long experience) {
		this.languageType = languageType;
		this.rowsAdded = rowsAdded;
		this.rowsDeleted = rowsDeleted;
		this.commits = commits;
		this.experience = experience;
		this.validateSelf();
	}
}

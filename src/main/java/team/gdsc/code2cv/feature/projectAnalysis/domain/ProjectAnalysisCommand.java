package team.gdsc.code2cv.feature.projectAnalysis.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import team.gdsc.code2cv.feature.project.entity.Project;
import team.gdsc.code2cv.feature.user.entity.User;

public class ProjectAnalysisCommand {

	@Builder
	@Getter
	public static class Create {
		private final User users;
		private final Project project;
		private final String contributeLinesCount;
		private final String contributeCommitCount;
		private final LocalDateTime contributeDays;

		public Create(User users, Project project, String contributeLinesCount, String contributeCommitCount,
				LocalDateTime contributeDays) {
			this.users = users;
			this.project = project;
			this.contributeLinesCount = contributeLinesCount;
			this.contributeCommitCount = contributeCommitCount;
			this.contributeDays = contributeDays;
		}
	}
}

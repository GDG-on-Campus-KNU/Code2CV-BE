package team.gdsc.code2cv.feature.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gdsc.code2cv.feature.project.domain.ProjectAnalysisCommitMessageCreate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProjectAnalysisCommitMessage {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private ProjectAnalysis projectAnalysis;

	private String commitMessageEntire;

	private String analysisResult;

	private Integer numberOfCommits;

	private String weiredCommits;

	@Builder
	private ProjectAnalysisCommitMessage(ProjectAnalysis projectAnalysis, String commitMessageEntire,
		String analysisResult,
		Integer numberOfCommits, String weiredCommits) {
		this.projectAnalysis = projectAnalysis;
		this.commitMessageEntire = commitMessageEntire;
		this.analysisResult = analysisResult;
		this.numberOfCommits = numberOfCommits;
		this.weiredCommits = weiredCommits;
	}

	public static ProjectAnalysisCommitMessage create(ProjectAnalysisCommitMessageCreate command,
		ProjectAnalysis projectAnalysis) {
		return ProjectAnalysisCommitMessage.builder()
			.projectAnalysis(projectAnalysis)
			.commitMessageEntire(command.getCommitMessageEntire())
			.analysisResult(command.getAnalysisResult())
			.numberOfCommits(command.getNumberOfCommits())
			.weiredCommits(command.getWeiredCommits())
			.build();
	}
}

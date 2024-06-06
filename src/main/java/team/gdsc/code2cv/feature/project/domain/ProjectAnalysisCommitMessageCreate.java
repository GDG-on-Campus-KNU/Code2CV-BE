package team.gdsc.code2cv.feature.project.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import team.gdsc.code2cv.core.utils.SelfValidating;

@Getter
@Builder
public class ProjectAnalysisCommitMessageCreate extends SelfValidating<ProjectAnalysisCommitMessageCreate> {

	@NotBlank
	private final String commitMessageEntire;
	@NotBlank
	private final String analysisResult;
	@NotBlank
	private final Integer numberOfCommits;
	@NotBlank
	private final String weiredCommits;

	public ProjectAnalysisCommitMessageCreate(String commitMessageEntire, String analysisResult,
		Integer numberOfCommits, String weiredCommits) {
		this.commitMessageEntire = commitMessageEntire;
		this.analysisResult = analysisResult;
		this.numberOfCommits = numberOfCommits;
		this.weiredCommits = weiredCommits;
		this.validateSelf();
	}
}

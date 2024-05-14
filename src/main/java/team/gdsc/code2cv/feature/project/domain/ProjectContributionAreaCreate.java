package team.gdsc.code2cv.feature.project.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import team.gdsc.code2cv.core.utils.SelfValidating;

@Getter
@Builder
public class ProjectContributionAreaCreate extends SelfValidating<ProjectContributionAreaCreate> {
	@Min(0)
	private Integer commitsCount;
	@Min(0)
	private Integer linesCount;
	@NotNull
	private String task; //TODO Enum으로 변경예정
	@NotNull
	private String detail; //TODO Enum으로 변경예정

	public ProjectContributionAreaCreate(Integer commitsCount, Integer linesCount, String task, String detail) {
		this.commitsCount = commitsCount;
		this.linesCount = linesCount;
		this.task = task;
		this.detail = detail;
		this.validateSelf();
	}
}

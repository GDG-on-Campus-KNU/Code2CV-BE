package team.gdsc.code2cv.feature.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gdsc.code2cv.feature.project.domain.ProjectContributionAreaCreate;

@Entity(name = "project_contribution_area")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProjectContributionArea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_analysis_id")
	private ProjectAnalysis projectAnalysis;

	private Integer commitsCount;

	private Integer linesCount;

	//직무
	private String task; //TODO Enum으로 변경예정

	//세부영역
	private String detail; //TODO Enum으로 변경예정

	@Builder
	private ProjectContributionArea(ProjectAnalysis projectAnalysis, Integer commitsCount, Integer linesCount,
		String task, String detail) {
		this.projectAnalysis = projectAnalysis;
		this.commitsCount = commitsCount;
		this.linesCount = linesCount;
		this.task = task;
		this.detail = detail;
	}

	public static ProjectContributionArea create(ProjectContributionAreaCreate command,
		ProjectAnalysis projectAnalysis) {
		return ProjectContributionArea.builder()
			.projectAnalysis(projectAnalysis)
			.commitsCount(command.getCommitsCount())
			.linesCount(command.getLinesCount())
			.task(command.getTask())
			.detail(command.getDetail())
			.build();
	}

}

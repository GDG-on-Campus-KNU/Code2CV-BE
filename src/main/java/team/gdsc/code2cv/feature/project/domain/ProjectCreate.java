package team.gdsc.code2cv.feature.project.domain;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import team.gdsc.code2cv.core.utils.SelfValidating;

@Getter
@Builder
public class ProjectCreate extends SelfValidating<ProjectCreate> {

	@NotBlank
	private final String url;
	@NotBlank
	private final String name;
	@NotBlank
	private final List<Tech> techs;
	@Min(0)
	private final Integer stars;
	@Min(0)
	private final Integer contributors;
	@Min(0)
	private final Integer forks;
	@Min(1)
	private final Integer commits;

	public ProjectCreate(String url, String name, List<Tech> techs, Integer stars, Integer contributors, Integer forks,
		Integer commits) {
		this.url = url;
		this.name = name;
		this.techs = techs;
		this.stars = stars;
		this.contributors = contributors;
		this.forks = forks;
		this.commits = commits;
		this.validateSelf();
	}
}

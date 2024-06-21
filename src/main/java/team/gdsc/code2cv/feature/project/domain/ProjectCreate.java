package team.gdsc.code2cv.feature.project.domain;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import team.gdsc.code2cv.core.utils.SelfValidating;

@Getter
@Builder
public class ProjectCreate extends SelfValidating<ProjectCreate> {
	private static final String TECHS_DELIMITER = ",";

	@NotBlank(message = "url은 필수입니다.")
	private final String url;
	@NotBlank(message = "name은 필수입니다.")
	private final String name;
	@NotNull(message = "techs는 필수입니다.")
	private final List<Tech> techs;
	@Min(value = 0, message = "stars는 0 이상이어야 합니다.")
	private final Integer stars;
	@Min(value = 0, message = "contributors는 0 이상이어야 합니다.")
	private final Integer contributors;
	@Min(value = 0, message = "forks는 0 이상이어야 합니다.")
	private final Integer forks;
	@Min(value = 0, message = "commits는 0 이상이어야 합니다.")
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
		techsValidation(techs);
		this.validateSelf();
	}

	public void techsValidation(List<Tech> techs) {
		for(Tech tech : techs) {// ,가 포함되어 있는지 확인
			if(tech.toString().contains(TECHS_DELIMITER)) {
				throw new IllegalArgumentException("Techs 에 ,가 포함되어 있습니다.");
			}
		}
	}
}

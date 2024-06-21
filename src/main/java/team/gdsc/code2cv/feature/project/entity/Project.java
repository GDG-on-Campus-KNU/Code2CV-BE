package team.gdsc.code2cv.feature.project.entity;

import java.util.List;
import java.util.stream.Stream;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gdsc.code2cv.feature.project.domain.ProjectCreate;
import team.gdsc.code2cv.feature.project.domain.Tech;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String url;

	private String name;

	private String techs;

	private Integer stars;

	private Integer contributors;

	private Integer forks;

	private Integer commits;


	private static final String TECHS_DELIMITER = ",";
	private static final String EMPTY_TECHS = "EMPTY";

	/**
	 * 내부 String으로 비정규화된 기술들을 List로 변환하여 반환
	 */
	public List<Tech> getTechs() {
		if(techs.equals(EMPTY_TECHS)) // [EMPTY_TECHS]는 빈 List로 반환
			return List.of();

		return Stream.of(techs.split(TECHS_DELIMITER))
			.map(Tech::valueOf)
			.toList();
	}

	@Builder
	private Project(String url, String name, List<Tech> techs, Integer stars, Integer contributors, Integer forks,
		Integer commits) {
		this.url = url;
		this.name = name;
		this.techs = techs.stream()
			.map(Tech::name)
			.reduce((a, b) -> a + TECHS_DELIMITER + b)
			.orElse(EMPTY_TECHS);
		this.stars = stars;
		this.contributors = contributors;
		this.forks = forks;
		this.commits = commits;
	}

	public static Project create(ProjectCreate command) {
		return Project.builder()
			.url(command.getUrl())
			.name(command.getName())
			.techs(command.getTechs())
			.stars(command.getStars())
			.contributors(command.getContributors())
			.forks(command.getForks())
			.commits(command.getCommits())
			.build();
	}

}

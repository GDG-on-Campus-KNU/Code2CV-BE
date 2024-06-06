package team.gdsc.code2cv.feature.project.entity;

import java.util.List;

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

	private List<Tech> techs;

	private Integer stars;

	private Integer contributors;

	private Integer forks;

	private Integer commits;

	@Builder
	private Project(String url, String name, List<Tech> techs, Integer stars, Integer contributors, Integer forks,
		Integer commits) {
		this.url = url;
		this.name = name;
		this.techs = techs;
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

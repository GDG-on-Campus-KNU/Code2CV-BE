package team.gdsc.code2cv.feature.projectAnalysis.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gdsc.code2cv.feature.project.entity.Project;
import team.gdsc.code2cv.feature.user.entity.User;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class projectAnalysis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private User users;
	@ManyToOne(fetch = FetchType.LAZY)
	private Project project;
	private String contributeLinesCount;
	private String contributeCommitCount;
	private LocalDateTime contributeDays;

	public static projectAnalysis create(Project project, User user, String contributeLinesCount,
			String contributeCommitCount, LocalDateTime contributeDays) {
		return projectAnalysis.builder().project(project).users(user).contributeLinesCount(contributeLinesCount)
				.contributeCommitCount(contributeCommitCount).contributeDays(contributeDays).build();
	}
}

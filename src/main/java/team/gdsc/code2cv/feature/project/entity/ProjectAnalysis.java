package team.gdsc.code2cv.feature.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import team.gdsc.code2cv.feature.project.domain.ProjectAnalysisCreate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProjectAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; //TODO UserEntity로 변경예정 ManyToOne

    private Long projectId; //TODO ProjectEntity로 변경예정 ManyToOne

    private Integer contributeLinesCount;

    private Integer contributeCommitsCount;

    private Integer contributeDays;

    @Builder
    private ProjectAnalysis(Long userId, Long projectId, Integer contributeLinesCount, Integer contributeCommitsCount, Integer contributeDays) {
        this.userId = userId;
        this.projectId = projectId;
        this.contributeLinesCount = contributeLinesCount;
        this.contributeCommitsCount = contributeCommitsCount;
        this.contributeDays = contributeDays;
    }

    public static ProjectAnalysis create(ProjectAnalysisCreate command, Long userId, Long projectId) {
        return ProjectAnalysis.builder()
                .userId(userId)
                .projectId(projectId)
                .contributeLinesCount(command.getContributeLinesCount())
                .contributeCommitsCount(command.getContributeCommitsCount())
                .contributeDays(command.getContributeDays())
                .build();
    }
}

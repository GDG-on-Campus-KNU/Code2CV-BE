package team.gdsc.code2cv.feature.project.domain;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import team.gdsc.code2cv.core.utils.SelfValidating;

@Getter
@Builder
public class ProjectAnalysisCreate extends SelfValidating<ProjectAnalysisCreate> {
    @Min(0)
    private final Integer contributeLinesCount;
    @Min(0)
    private final Integer contributeCommitsCount;
    @Min(0)
    private final Integer contributeDays;

    public ProjectAnalysisCreate(Integer contributeLinesCount, Integer contributeCommitsCount, Integer contributeDays) {
        this.contributeLinesCount = contributeLinesCount;
        this.contributeCommitsCount = contributeCommitsCount;
        this.contributeDays = contributeDays;
        this.validateSelf();
    }
}

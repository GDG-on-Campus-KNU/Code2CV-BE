package team.gdsc.code2cv.feature.project.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectAnalysisCreate {
    private final Integer contributeLinesCount;
    private final Integer contributeCommitsCount;
    private final Integer contributeDays;
}

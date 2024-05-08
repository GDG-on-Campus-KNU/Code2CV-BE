package team.gdsc.code2cv.feature.project.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectContributionAreaCreate {
    private Integer commitsCount;
    private Integer linesCount;
    private String task; //TODO Enum으로 변경예정
    private String detail; //TODO Enum으로 변경예정
}

package team.gdsc.code2cv.feature.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gdsc.code2cv.feature.project.entity.ProjectAnalysis;

public interface ProjectAnalysisRepository extends JpaRepository<ProjectAnalysis, Long> {
}

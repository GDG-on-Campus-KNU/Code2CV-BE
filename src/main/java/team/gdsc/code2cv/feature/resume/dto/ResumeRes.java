package team.gdsc.code2cv.feature.resume.dto;

import java.util.List;

import lombok.Builder;
import team.gdsc.code2cv.feature.resume.domain.activity.Activity;
import team.gdsc.code2cv.feature.resume.domain.career.Career;
import team.gdsc.code2cv.feature.resume.domain.certification.Certification;
import team.gdsc.code2cv.feature.resume.domain.education.Education;
import team.gdsc.code2cv.feature.resume.entity.Resume;
import team.gdsc.code2cv.feature.user.entity.User;

public class ResumeRes {
	@Builder
	public record ResumeDto(
		Long id,
		String name,
		Boolean isDone,
		Boolean isDefault,
		String file
	) {
		public static ResumeDto from(Resume resume) {
			return ResumeDto.builder()
				.id(resume.getId())
				.name(resume.getName())
				.isDone(resume.getIsDone())
				.isDefault(resume.getIsDefault())
				.file(resume.getFile())
				.build();
		}
	}

	@Builder
	public record ResumeDetailDto(
		Long id,
		User user,
		String name,
		Boolean isDone,
		Boolean isDefault,
		String file,
		List<Career> careers,
		List<Activity> activities,
		List<Certification> certifications,
		List<Education> educations
	) {
		public static ResumeDetailDto from(Resume resume) {
			return ResumeDetailDto.builder()
				.id(resume.getId())
				.user(resume.getUser())
				.name(resume.getName())
				.isDone(resume.getIsDone())
				.isDefault(resume.getIsDefault())
				.file(resume.getFile())
				.careers(resume.getCareers())
				.activities(resume.getActivities())
				.certifications(resume.getCertifications())
				.educations(resume.getEducations())
				.build();
		}
	}
}

package team.gdsc.code2cv.feature.resume.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import team.gdsc.code2cv.feature.resume.domain.activity.Activity;
import team.gdsc.code2cv.feature.resume.domain.career.Career;
import team.gdsc.code2cv.feature.resume.domain.certification.Certification;
import team.gdsc.code2cv.feature.resume.domain.education.Education;

public class ResumeReq {
	public record CreateByNewRequest(
		@NotBlank(message = "이름은 필수입니다.")
		String name,

		@NotNull(message = "isDone은 필수입니다.")
		Boolean isDone
	) {
	}

	public record CreateByUploadRequest(
		@NotBlank(message = "이름은 필수입니다.")
		String name,

		@NotBlank(message = "파일은 필수입니다.")
		String file
	) {
	}

	public record UpdateRequest(
		@NotBlank(message = "이름은 필수입니다.")
		String name,

		@NotNull(message = "isDone은 필수입니다.")
		Boolean isDone,

		@NotNull(message = "isDefault은 필수입니다.")
		Boolean isDefault,

		@NotNull(message = "경력은 필수입니다.")
		List<Career> careers,

		@NotNull(message = "활동은 필수입니다.")
		List<Activity> activities,

		@NotNull(message = "자격증은 필수입니다.")
		List<Certification> certifications,

		@NotNull(message = "학력은 필수입니다.")
		List<Education> educations
	) {
	}
}

package team.gdsc.code2cv.feature.resume.entity;

import java.util.Collections;
import java.util.List;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gdsc.code2cv.feature.resume.domain.ResumeCommand;
import team.gdsc.code2cv.feature.resume.domain.activity.Activity;
import team.gdsc.code2cv.feature.resume.domain.activity.ActivityListConverter;
import team.gdsc.code2cv.feature.resume.domain.career.Career;
import team.gdsc.code2cv.feature.resume.domain.career.CareerListConverter;
import team.gdsc.code2cv.feature.resume.domain.certification.Certification;
import team.gdsc.code2cv.feature.resume.domain.certification.CertificationListConverter;
import team.gdsc.code2cv.feature.resume.domain.education.Education;
import team.gdsc.code2cv.feature.resume.domain.education.EducationListConverter;
import team.gdsc.code2cv.feature.resume.dto.ResumeReq;
import team.gdsc.code2cv.global.jwt.JwtUser;
import team.gdsc.code2cv.global.repository.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
public class Resume extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId;

	private String name;

	private Boolean isDone;

	private Boolean isDefault;

	private String file;

	@Convert(converter = CareerListConverter.class)
	private List<Career> careers;

	@Convert(converter = ActivityListConverter.class)
	private List<Activity> activities;

	@Convert(converter = CertificationListConverter.class)
	private List<Certification> certifications;

	@Convert(converter = EducationListConverter.class)
	private List<Education> educations;

	public void update(ResumeReq.UpdateRequest request) {
		this.name = request.name();
		this.isDone = request.isDone();
		this.isDefault = request.isDefault();
		this.careers = request.careers();
		this.activities = request.activities();
		this.certifications = request.certifications();
		this.educations = request.educations();
	}

	public static Resume create(ResumeReq.CreateByNewRequest request, JwtUser jwtUser) {
		return Resume.builder()
			.userId(jwtUser.getId())
			.name(request.name())
			.isDone(request.isDone())
			.isDefault(false)
			.file(null)
			.careers(Collections.emptyList())
			.activities(Collections.emptyList())
			.certifications(Collections.emptyList())
			.educations(Collections.emptyList())
			.build();
	}

	public static Resume create(ResumeReq.CreateByUploadRequest request, JwtUser jwtUser) {
		return Resume.builder()
			.userId(jwtUser.getId())
			.name(request.name())
			.isDone(true)
			.isDefault(false)
			.file(request.file())
			.careers(Collections.emptyList())
			.activities(Collections.emptyList())
			.certifications(Collections.emptyList())
			.educations(Collections.emptyList())
			.build();
	}

	public static Resume create(ResumeCommand.CreateByNew command, JwtUser jwtUser) {
		return Resume.builder()
			.userId(jwtUser.getId())
			.name(command.getName())
			.isDone(command.getIsDone())
			.isDefault(false)
			.file(null)
			.build();

	}

	public static Resume create(ResumeCommand.CreateByUpload command, JwtUser jwtUser) {
		return Resume.builder()
			.userId(jwtUser.getId())
			.name(command.getName())
			.isDone(true)
			.isDefault(false)
			.file(command.getFile())
			.build();
	}
}

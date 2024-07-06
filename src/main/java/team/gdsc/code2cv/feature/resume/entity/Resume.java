package team.gdsc.code2cv.feature.resume.entity;

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

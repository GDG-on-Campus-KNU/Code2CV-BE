package team.gdsc.code2cv.feature.resume.domain.education;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class EducationListConverterTest {

	private final EducationListConverter educationListConverter = new EducationListConverter();

	@Test
	void convertToDatabaseColumn() {
		// given
		List<Education> educations = List.of(
			Education.builder()
				.index(1)
				.startDate("2017-03-01")
				.endDate("2021-02-28")
				.school("학교1")
				.major("전공1")
				.status(EducationStatus.GRADUATED)
				.build(),
			Education.builder()
				.index(2)
				.startDate("2021-03-01")
				.endDate("2025-02-28")
				.school("학교2")
				.major("전공2")
				.status(EducationStatus.ENROLLMENT)
				.build()
		);

		// when
		String result = educationListConverter.convertToDatabaseColumn(educations);
		List<Education> result2 = educationListConverter.convertToEntityAttribute(result);

		// then
		assertThat(result).isEqualTo(
			"[{\"index\":1,\"startDate\":\"2017-03-01\",\"endDate\":\"2021-02-28\",\"school\":\"학교1\",\"major\":\"전공1\",\"status\":\"GRADUATED\"},{\"index\":2,\"startDate\":\"2021-03-01\",\"endDate\":\"2025-02-28\",\"school\":\"학교2\",\"major\":\"전공2\",\"status\":\"ENROLLMENT\"}]");

		assertThat(result2).hasSize(2);

		assertThat(result2.get(0).getIndex()).isEqualTo(1);
		assertThat(result2.get(0).getSchool()).isEqualTo("학교1");
		assertThat(result2.get(0).getMajor()).isEqualTo("전공1");
		assertThat(result2.get(0).getStatus()).isEqualTo(EducationStatus.GRADUATED);
		assertThat(result2.get(0).getStartDate()).isEqualTo("2017-03-01");
		assertThat(result2.get(0).getEndDate()).isEqualTo("2021-02-28");

		assertThat(result2.get(1).getIndex()).isEqualTo(2);
		assertThat(result2.get(1).getSchool()).isEqualTo("학교2");
		assertThat(result2.get(1).getMajor()).isEqualTo("전공2");
		assertThat(result2.get(1).getStatus()).isEqualTo(EducationStatus.ENROLLMENT);
		assertThat(result2.get(1).getStartDate()).isEqualTo("2021-03-01");
		assertThat(result2.get(1).getEndDate()).isEqualTo("2025-02-28");
	}

	@Test
	void convertToEntityAttribute() {
		// given
		String dbData = "[{\"index\":1,\"startDate\":\"2017-03-01\",\"endDate\":\"2021-02-28\",\"school\":\"학교1\",\"major\":\"전공1\",\"status\":\"GRADUATED\"},{\"index\":2,\"startDate\":\"2021-03-01\",\"endDate\":\"2025-02-28\",\"school\":\"학교2\",\"major\":\"전공2\",\"status\":\"ENROLLMENT\"}]";

		// when
		List<Education> result = educationListConverter.convertToEntityAttribute(dbData);

		// then
		assertThat(result).hasSize(2);

		assertThat(result.get(0).getIndex()).isEqualTo(1);
		assertThat(result.get(0).getSchool()).isEqualTo("학교1");
		assertThat(result.get(0).getMajor()).isEqualTo("전공1");
		assertThat(result.get(0).getStatus()).isEqualTo(EducationStatus.GRADUATED);
		assertThat(result.get(0).getStartDate()).isEqualTo("2017-03-01");
		assertThat(result.get(0).getEndDate()).isEqualTo("2021-02-28");

		assertThat(result.get(1).getIndex()).isEqualTo(2);
		assertThat(result.get(1).getSchool()).isEqualTo("학교2");
		assertThat(result.get(1).getMajor()).isEqualTo("전공2");
		assertThat(result.get(1).getStatus()).isEqualTo(EducationStatus.ENROLLMENT);
		assertThat(result.get(1).getStartDate()).isEqualTo("2021-03-01");
		assertThat(result.get(1).getEndDate()).isEqualTo("2025-02-28");
	}
}
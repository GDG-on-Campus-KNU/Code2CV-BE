package team.gdsc.code2cv.feature.resume.domain.certification;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class CertificationListConverterTest {

	private final CertificationListConverter certificationListConverter = new CertificationListConverter();

	@Test
	void convertToDatabaseColumn() {
		// given
		List<Certification> certificationList = List.of(
			Certification.builder()
				.index(1)
				.name("자격증1")
				.date("2021-01-01")
				.organization("한국시험원")
				.build(),
			Certification.builder()
				.index(2)
				.name("자격증2")
				.date("2021-02-01")
				.organization("한국시험원")
				.build()
		);

		// when
		String result = certificationListConverter.convertToDatabaseColumn(certificationList);
		List<Certification> result2 = certificationListConverter.convertToEntityAttribute(result);

		// then
		assertThat(result).isEqualTo(
			"[{\"index\":1,\"name\":\"자격증1\",\"date\":\"2021-01-01\",\"organization\":\"한국시험원\"},{\"index\":2,\"name\":\"자격증2\",\"date\":\"2021-02-01\",\"organization\":\"한국시험원\"}]");

		assertThat(result2).hasSize(2);

		assertThat(result2.get(0).getIndex()).isEqualTo(1);
		assertThat(result2.get(0).getName()).isEqualTo("자격증1");
		assertThat(result2.get(0).getDate()).isEqualTo("2021-01-01");
		assertThat(result2.get(0).getOrganization()).isEqualTo("한국시험원");

		assertThat(result2.get(1).getIndex()).isEqualTo(2);
		assertThat(result2.get(1).getName()).isEqualTo("자격증2");
		assertThat(result2.get(1).getDate()).isEqualTo("2021-02-01");
		assertThat(result2.get(1).getOrganization()).isEqualTo("한국시험원");
	}

	@Test
	void convertToEntityAttribute() {
		// given
		String dbData = "[{\"index\":1,\"name\":\"자격증1\",\"date\":\"2021-01-01\",\"organization\":\"한국시험원\"},{\"index\":2,\"name\":\"자격증2\",\"date\":\"2021-02-01\",\"organization\":\"한국시험원\"}]";

		// when
		List<Certification> result = certificationListConverter.convertToEntityAttribute(dbData);

		// then
		assertThat(result).hasSize(2);

		assertThat(result.get(0).getIndex()).isEqualTo(1);
		assertThat(result.get(0).getName()).isEqualTo("자격증1");
		assertThat(result.get(0).getDate()).isEqualTo("2021-01-01");
		assertThat(result.get(0).getOrganization()).isEqualTo("한국시험원");

		assertThat(result.get(1).getIndex()).isEqualTo(2);
		assertThat(result.get(1).getName()).isEqualTo("자격증2");
		assertThat(result.get(1).getDate()).isEqualTo("2021-02-01");
		assertThat(result.get(1).getOrganization()).isEqualTo("한국시험원");
	}
}
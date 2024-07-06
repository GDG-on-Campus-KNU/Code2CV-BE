package team.gdsc.code2cv.feature.resume.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import team.gdsc.code2cv.feature.resume.domain.activity.Activity;
import team.gdsc.code2cv.feature.resume.domain.activity.ActivityListConverter;

class ActivityListConverterTest {

	private final ActivityListConverter converter = new ActivityListConverter();

	@Test
	void testConvertToDatabaseColumn() {
		// given
		List<Activity> list = List.of(
			Activity.builder()
				.index(1)
				.name("name1")
				.startDate("2021-01-01")
				.endDate("2021-12-31")
				.content("content1")
				.build(),
			Activity.builder()
				.index(2)
				.name("name2")
				.startDate("2022-01-01")
				.endDate("2022-12-31")
				.content("content2")
				.build()
		);
		// when

		String result = converter.convertToDatabaseColumn(list);
		List<Activity> result2 = converter.convertToEntityAttribute(result);

		// then
		assertThat(result2).hasSize(2);

		assertThat(result2.get(0).getIndex()).isEqualTo(1);
		assertThat(result2.get(0).getName()).isEqualTo("name1");
		assertThat(result2.get(0).getStartDate()).isEqualTo("2021-01-01");
		assertThat(result2.get(0).getEndDate()).isEqualTo("2021-12-31");
		assertThat(result2.get(0).getContent()).isEqualTo("content1");

		assertThat(result2.get(1).getIndex()).isEqualTo(2);
		assertThat(result2.get(1).getName()).isEqualTo("name2");
		assertThat(result2.get(1).getStartDate()).isEqualTo("2022-01-01");
		assertThat(result2.get(1).getEndDate()).isEqualTo("2022-12-31");
		assertThat(result2.get(1).getContent()).isEqualTo("content2");
	}

	@Test
	void testConvertToEntityAttribute() {
		// given
		String dbData = "[{\"index\":1,\"name\":\"name1\",\"startDate\":\"2021-01-01\",\"endDate\":\"2021-12-31\",\"content\":\"content1\"},{\"index\":2,\"name\":\"name2\",\"startDate\":\"2022-01-01\",\"endDate\":\"2022-12-31\",\"content\":\"content2\"}]";
		// when
		List<Activity> result = converter.convertToEntityAttribute(dbData);
		// then
		assertThat(result).hasSize(2);

		assertThat(result.get(0).getIndex()).isEqualTo(1);
		assertThat(result.get(0).getName()).isEqualTo("name1");
		assertThat(result.get(0).getStartDate()).isEqualTo("2021-01-01");
		assertThat(result.get(0).getEndDate()).isEqualTo("2021-12-31");
		assertThat(result.get(0).getContent()).isEqualTo("content1");

		assertThat(result.get(1).getIndex()).isEqualTo(2);
		assertThat(result.get(1).getName()).isEqualTo("name2");
		assertThat(result.get(1).getStartDate()).isEqualTo("2022-01-01");
		assertThat(result.get(1).getEndDate()).isEqualTo("2022-12-31");
		assertThat(result.get(1).getContent()).isEqualTo("content2");
	}
}
package team.gdsc.code2cv.feature.resume.domain.career;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class CareerListConverterTest {

	private final CareerListConverter converter = new CareerListConverter();

	@Test
	void convertToDatabaseColumn() {
		// given
		List<Career> list = List.of(
			Career.builder()
				.index(1)
				.title("title1")
				.content("content1")
				.startDate("2021-01-01")
				.endDate("2021-12-31")
				.isWorking(true)
				.type(CareerType.INTERN)
				.build(),
			Career.builder()
				.index(2)
				.title("title2")
				.content("content2")
				.startDate("2022-01-01")
				.endDate("2022-12-31")
				.isWorking(false)
				.type(CareerType.FREELANCE)
				.build()
		);

		// when
		String result = converter.convertToDatabaseColumn(list);
		List<Career> result2 = converter.convertToEntityAttribute(result);

		// then
		assertThat(result2).hasSize(2);

		assertThat(result2.get(0).getIndex()).isEqualTo(1);
		assertThat(result2.get(0).getTitle()).isEqualTo("title1");
		assertThat(result2.get(0).getContent()).isEqualTo("content1");
		assertThat(result2.get(0).getStartDate()).isEqualTo("2021-01-01");
		assertThat(result2.get(0).getEndDate()).isEqualTo("2021-12-31");
		assertThat(result2.get(0).getType()).isEqualTo(CareerType.INTERN);
		assertThat(result2.get(0).getIsWorking()).isTrue();

		assertThat(result2.get(1).getIndex()).isEqualTo(2);
		assertThat(result2.get(1).getTitle()).isEqualTo("title2");
		assertThat(result2.get(1).getContent()).isEqualTo("content2");
		assertThat(result2.get(1).getStartDate()).isEqualTo("2022-01-01");
		assertThat(result2.get(1).getEndDate()).isEqualTo("2022-12-31");
		assertThat(result2.get(1).getType()).isEqualTo(CareerType.FREELANCE);
		assertThat(result2.get(1).getIsWorking()).isFalse();
	}

	@Test
	void convertToEntityAttribute() {
		// given
		String dbData = "[{\"index\":1,\"startDate\":null,\"endDate\":null,\"title\":\"title1\",\"content\":\"content1\",\"isWorking\":true,\"type\":\"INTERN\"},{\"index\":2,\"startDate\":null,\"endDate\":null,\"title\":\"title2\",\"content\":\"content2\",\"isWorking\":false,\"type\":\"FREELANCE\"}]";

		// when
		List<Career> result = converter.convertToEntityAttribute(dbData);

		// then
		assertThat(result).hasSize(2);
		assertThat(result.get(0).getIndex()).isEqualTo(1);
		assertThat(result.get(0).getTitle()).isEqualTo("title1");
		assertThat(result.get(0).getContent()).isEqualTo("content1");
		assertThat(result.get(0).getStartDate()).isNull();
		assertThat(result.get(0).getEndDate()).isNull();

		assertThat(result.get(1).getIndex()).isEqualTo(2);
		assertThat(result.get(1).getTitle()).isEqualTo("title2");
		assertThat(result.get(1).getContent()).isEqualTo("content2");
		assertThat(result.get(1).getStartDate()).isNull();
		assertThat(result.get(1).getEndDate()).isNull();
		assertThat(result.get(1).getType()).isEqualTo(CareerType.FREELANCE);
		assertThat(result.get(1).getIsWorking()).isFalse();

	}
}

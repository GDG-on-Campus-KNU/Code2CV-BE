package team.gdsc.code2cv.feature.resume.domain.activity;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ActivityListConverter implements AttributeConverter<List<Activity>, String> {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<Activity> list) {
		try {
			return mapper.writeValueAsString(list);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Activity> convertToEntityAttribute(String dbData) {
		try {
			return mapper.readValue(dbData, mapper.getTypeFactory().constructCollectionType(List.class, Activity.class));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

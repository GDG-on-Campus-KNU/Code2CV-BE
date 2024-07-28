package team.gdsc.code2cv.feature.resume.domain.education;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EducationListConverter implements AttributeConverter<List<Education>, String> {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<Education> list) {
		try {
			return mapper.writeValueAsString(list);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Education> convertToEntityAttribute(String dbData) {
		try {
			return mapper.readValue(dbData, mapper.getTypeFactory().constructCollectionType(List.class, Education.class));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

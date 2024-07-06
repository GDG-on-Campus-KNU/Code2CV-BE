package team.gdsc.code2cv.feature.resume.domain;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CareerListConverter implements AttributeConverter<List<Career>, String> {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<Career> list) {
		try {
			return mapper.writeValueAsString(list);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Career> convertToEntityAttribute(String dbData) {
		try {
			return mapper.readValue(dbData, mapper.getTypeFactory().constructCollectionType(List.class, Career.class));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

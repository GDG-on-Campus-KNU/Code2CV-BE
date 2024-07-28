package team.gdsc.code2cv.feature.resume.domain.certification;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CertificationListConverter implements AttributeConverter<List<Certification>, String>{

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<Certification> list) {
		try {
			return mapper.writeValueAsString(list);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Certification> convertToEntityAttribute(String dbData) {
		try {
			return mapper.readValue(dbData, mapper.getTypeFactory().constructCollectionType(List.class, Certification.class));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

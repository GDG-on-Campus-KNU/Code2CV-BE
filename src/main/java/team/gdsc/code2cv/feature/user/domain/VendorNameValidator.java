package team.gdsc.code2cv.feature.user.domain;

import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import team.gdsc.code2cv.feature.user.entity.VendorName;

public class VendorNameValidator implements ConstraintValidator<ValidVendorName, String> {
	@Override
	public void initialize(ValidVendorName constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true; // `null`을 유효한 것으로 간주하거나 `false`로 설정하면 안 됨
		}
		try {
			Arrays.stream(VendorName.values())
				.map(VendorName::getName)
				.anyMatch(s -> s.equals(value));
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}

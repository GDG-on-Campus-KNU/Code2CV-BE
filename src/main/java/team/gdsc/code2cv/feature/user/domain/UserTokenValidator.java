package team.gdsc.code2cv.feature.user.domain;

import java.util.Optional;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import team.gdsc.code2cv.feature.user.entity.VendorName;

public class UserTokenValidator implements ConstraintValidator<ValidUserToken, String> {
	@Override
	public void initialize(ValidUserToken constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return Optional.ofNullable(value)
			.map(val -> val.split("_")[0])
			.map(VendorName::valid)
			.orElse(true);
	}
}

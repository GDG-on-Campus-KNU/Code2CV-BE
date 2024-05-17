package team.gdsc.code2cv.feature.user.domain;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VendorNameValidator.class)
public @interface ValidVendorName {
	String message() default "VendorName이 유효하지 않습니다.";

	Class<?>[] groups() default {};

	Class<?>[] payload() default {};
}

package team.gdsc.code2cv.feature.user.domain;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserTokenValidator.class)
public @interface ValidUserToken {
	String message() default "유효하지 않은 VendorName입니다.";

	Class<?>[] groups() default {};

	Class<?>[] payload() default {};
}

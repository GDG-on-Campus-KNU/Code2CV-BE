package team.gdsc.code2cv.core.utils;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * 검증이 필요한 객체에 대한 검증을 수행하는 추상 클래스 <br>
 * 검증이 필요한 객체는 이 클래스를 상속받아 validateSelf 메소드를 호출하여 검증을 수행한다. <p>
 * 생성자에서 this.validateSelf()를 호출하여 객체 생성 시 검증을 수행할 수 있다.
 *
 * @param <T> 검증이 필요한 객체의 타입
 */
public abstract class SelfValidating<T> {
	private final Validator validator;

	protected SelfValidating() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	protected void validateSelf() {
		Set<ConstraintViolation<T>> violations = validator.validate((T)this);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
	}
}

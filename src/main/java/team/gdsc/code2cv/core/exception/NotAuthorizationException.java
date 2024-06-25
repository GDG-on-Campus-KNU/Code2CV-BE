package team.gdsc.code2cv.core.exception;

public class NotAuthorizationException extends RuntimeException{
	public NotAuthorizationException(String message) {
		super(message);
	}

	public NotAuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotAuthorizationException(Throwable cause) {
		super(cause);
	}
}

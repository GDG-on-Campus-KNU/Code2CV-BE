package team.gdsc.code2cv.core.exception;

public class ExternalServerCommunicationException extends RuntimeException{
	public ExternalServerCommunicationException(String message) {
		super(message);
	}

	public ExternalServerCommunicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExternalServerCommunicationException(Throwable cause) {
		super(cause);
	}
}

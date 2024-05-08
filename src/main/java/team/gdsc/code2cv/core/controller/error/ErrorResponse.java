package team.gdsc.code2cv.core.controller.error;

import lombok.Builder;

@Builder
public record ErrorResponse(
	String debugMessage,
	ErrorCode code
) {
}

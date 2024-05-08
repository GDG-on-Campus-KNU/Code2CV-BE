package team.gdsc.code2cv.global.controller.error;

import lombok.Builder;

@Builder
public record ErrorResponse(
	String debugMessage,
	String code
) {
}

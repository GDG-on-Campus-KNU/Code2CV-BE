package team.gdsc.code2cv.global.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.gdsc.code2cv.global.controller.error.ErrorCode;
import team.gdsc.code2cv.global.controller.error.ErrorResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		log.debug("Token : {}", request.getHeader("Authorization"));
		ErrorResponse fail = ErrorResponse.builder()
			.code(ErrorCode.JWT.name())
			.debugMessage(authException.getMessage())
			.build();
		response.setStatus(401);
		String json = objectMapper.writeValueAsString(fail);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
	}
}

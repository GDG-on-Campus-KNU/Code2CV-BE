package team.gdsc.code2cv.global.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team.gdsc.code2cv.global.jwt.JwtProvider;
import team.gdsc.code2cv.global.jwt.JwtUser;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;

	private static final String AUTHORIZATION_HEADER = "Authorization";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		log.info("JwtAuthenticationFilter");
		String rawToken;
		/**
		 * Authorization 헤더에서 토큰을 추출한다.
		 * 토큰이 없거나 올바르지 filterChain을 통해 다음 필터로 넘긴다.
		 */
		try {
			rawToken = jwtProvider.parseHeader(request.getHeader(AUTHORIZATION_HEADER));
		} catch (Exception e) {
			filterChain.doFilter(request, response);
			return;
		}

		/**
		 * JWT 구현에서 AuthenicationFilter 역할인 유효성 검사까지하고, AuthenticationManager를
		 * 통하지 않고 바로 Provider 객체를 직접 구현하여 Authentication 객체를 생성한다.
		 *
		 * 토큰이 존재하다면 토큰을 검증하고 Authentication 객체를 SecurityContext에 저장한다.
		 * ContextHolder에 인증정보가 저장되면, Spring Security는 해당 요청에 대해 인증된 사용자로 간주한다.
		 * 즉, 다음 AutthenticationManager를 거치지 않게 된다.
		 *
		 * 본래의 시큐리티에서는 AuthenticationManager 인터페이스를 통해 인증을 진행하고,
		 * AuthenticationManager는 AuthenicationProvider에게 Authentication 객체를 전달한다.(UsernamePasswordAuthenticationToken)
		 * UserDetailsService를 통해 사용자 정보를 가져와 인증을 진행한다.
		 * 인증을 통해 UserDetails 객체를 생성하고 이를 SecurityContext에 저장한다.
		 *
		 * 성공시 AuthenticationSuccessHandler, 실패시 AuthenticationFailureHandler를 호출한다.
		 */
		if (jwtProvider.validateToken(rawToken)) {
			JwtUser jwtUser = jwtProvider.getJwtUser(rawToken);
			Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUser, null,
				jwtUser.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}
}

package team.gdsc.code2cv.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring()
			.requestMatchers("/v3/api-docs/**")
			.requestMatchers("/h2-console/**")
			.requestMatchers("/static/**");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
		http.sessionManagement((session) -> session
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests((authorize) ->
			authorize
				.requestMatchers(
					"/login", "/signup", "/", "/user",
					"/api/auth/**",
					"/swagger-ui/**"
				).permitAll()
				.anyRequest().authenticated()
		);

		/**
		 * 시큐리티 필터체인에서의 예외처리
		 * authenticated() 메서드를 통해 인증된 사용자만 접근 가능하도록 설정한다.
		 * 1. 인증 예외 처리 (인증되지 않은 사용자) 401
		 * 2. 인가 예외 처리 (권한이 없는 사용자) 403
		 */
		http.exceptionHandling((exception) -> exception
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)
		);

		/**
		 * JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가한다.
		 * UsernamePasswordAuthenticationFilter는 폼 로그인을 처리하는 필터이다.
		 * JWT 인증 필터를 통과하면 UsernamePasswordAuthenticationFilter를 거치지 않고 인증된 사용자로 간주한다.
		 * 즉, 로그인폼에서 진행되던 username, password를 통한 인증을 JWT 인증 필터에서 처리한다.
		 */
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

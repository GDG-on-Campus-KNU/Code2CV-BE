package team.gdsc.code2cv.global.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import team.gdsc.code2cv.core.exception.NotAuthorizationException;
import team.gdsc.code2cv.feature.user.entity.Role;

@Component
public class JwtProvider implements InitializingBean {
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.access-token-expire-time}")
	private long accessTokenExpireTime;
	@Value("${jwt.refresh-token-expire-time}")
	private long refreshTokenExpireTime;
	private Key secretKey;
	private static final String ROLE = "role";
	private static final String IS_ACCESS_TOKEN = "isAccessToken";
	private static final String HEADER_PREFIX = "Bearer ";

	public String parseHeader(String header) {
		if (header == null || header.isEmpty()) {
			throw new IllegalArgumentException("Authorization 헤더가 없습니다.");
		} else if (!header.startsWith(HEADER_PREFIX)) {
			throw new IllegalArgumentException("Authorization 올바르지 않습니다.");
		} else if (header.split(" ").length != 2) {
			throw new IllegalArgumentException("Authorization 올바르지 않습니다.");
		}

		return header.split(" ")[1];
	}

	public JwtToken createToken(JwtUser jwtUser) {
		String accessToken = generateToken(jwtUser, true);
		String refreshToken = generateToken(jwtUser, false);
		return JwtToken.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	/**
	 * Jwt가 유효한지 검사하는 메서드.
	 * 만료시간, 토큰의 유효성을 검사한다.
	 */
	public boolean validateToken(String rawToken) {
		try {
			Claims claims = extractClaims(rawToken);
			System.out.println("rawToken claims:" + claims.toString());
			return !claims.getExpiration().before(new Date());
		} catch (Exception e) {    //JwtException, ExpiredJwtException, NullPointerException
			return false;
		}
	}

	/**
	 * [validateToken] 이후 호출하는 메서드.
	 * refreshToken을 통해, accessToken을 재발급하는 메서드.
	 * refreshToken의 유효성을 검사하고, isAccessToken이 true일때만 accessToken을 재발급한다.
	 */
	public String reissueAccessToken(String refreshToken) {
		Claims claims = extractClaims(refreshToken);
		if (claims.get(IS_ACCESS_TOKEN, Boolean.class)) {
			throw new NotAuthorizationException("RefreshToken이 유효하지 않습니다.");
		}
		JwtUser jwtUser = claimsToJwtUser(claims);
		return generateToken(jwtUser, true);

	}

	/**
	 * [validateToken] 이후 호출하는 메서드.
	 * rawToken을 통해 JwtUser를 추출한다.
	 * [jwtUser]는 userId와 role을 가지고 있다. 즉 JWT에 저장된 정보를 추출한다.
	 */
	public JwtUser getJwtUser(String rawToken) {
		Claims claims = extractClaims(rawToken);
		System.out.println("clamis:" + claims.toString());
		return claimsToJwtUser(claims);
	}

	private JwtUser claimsToJwtUser(Claims claims) {
		Role userRole = Role.valueOf(claims.get(ROLE, String.class));
		String userId = claims.getSubject();
		return JwtUser.of(Long.parseLong(userId), userRole);
	}

	/**
	 * Jwt 토큰생성
	 * accessToken과 refreshToken의 다른점은 만료시간과, isAccessToken이다.
	 */
	private String generateToken(JwtUser jwtUser, boolean isAccessToken) {
		long expireTime = isAccessToken ? accessTokenExpireTime : refreshTokenExpireTime;
		Date expireDate = new Date(System.currentTimeMillis() + expireTime);
		return Jwts.builder()
			.signWith(secretKey)
			.claim(ROLE, jwtUser.getRole().getValue())
			.claim(IS_ACCESS_TOKEN, isAccessToken)
			.setSubject(jwtUser.getId().toString())
			.setExpiration(expireDate)
			.compact();
	}


	private Claims extractClaims(String rawToken) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(rawToken)
			.getBody();
	}

	/**
	 * HS256방식의 키를 생성한다.
	 */
	@Override
	public void afterPropertiesSet() {
		secretKey = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
	}
}

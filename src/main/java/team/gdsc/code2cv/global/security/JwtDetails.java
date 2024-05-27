package team.gdsc.code2cv.global.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import team.gdsc.code2cv.feature.user.entity.Role;
import team.gdsc.code2cv.global.jwt.JwtUser;

@Getter
@Builder
public class JwtDetails implements UserDetails {
	private Long userId;
	private Role role;

	public static JwtDetails from(JwtUser jwtUser) {
		return JwtDetails.builder()
			.userId(jwtUser.getId())
			.role(jwtUser.getRole())
			.build();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getRoles()
			.stream()
			.map(role -> new SimpleGrantedAuthority(role.getValue()))
			.collect(Collectors.toSet());
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}

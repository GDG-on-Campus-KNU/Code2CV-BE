package team.gdsc.code2cv.feature.user.domain;

import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN"),
	NONE("ROLE_NONE");

	private final String value;

	public Set<Role> getRoles() {
		return switch (this) {
			case NONE -> Set.of(Role.NONE);
			case USER -> Set.of(Role.USER);
			case ADMIN -> Set.of(Role.USER, Role.ADMIN);
		};
	}
}

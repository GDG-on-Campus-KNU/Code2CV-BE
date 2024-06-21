package team.gdsc.code2cv.feature.user.entity;

import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	ROLE_USER("ROLE_USER"),
	ROLE_ADMIN("ROLE_ADMIN"),
	NONE("ROLE_NONE");

	private final String value;

	public Set<Role> getRoles() {
		return switch (this) {
			case NONE -> Set.of(Role.NONE);
			case ROLE_USER -> Set.of(Role.ROLE_USER);
			case ROLE_ADMIN -> Set.of(Role.ROLE_USER, Role.ROLE_ADMIN);
		};
	}
}

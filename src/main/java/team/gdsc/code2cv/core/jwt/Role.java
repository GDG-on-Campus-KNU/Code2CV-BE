package team.gdsc.code2cv.core.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");
    private final String value;

    public Set<Role> getRoles() {
        return switch (this) {
            case USER -> Set.of(Role.USER);
            case ADMIN -> Set.of(Role.USER, Role.ADMIN);
        };
    }
}

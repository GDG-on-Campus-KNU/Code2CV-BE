package team.gdsc.code2cv.core.jwt;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class JwtUser {
    private Long id;
    private Role role;

    public static JwtUser of(Long id, Role role) {
        return JwtUser.builder()
                .id(id)
                .role(role)
                .build();
    }


}

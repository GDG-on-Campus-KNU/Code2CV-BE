package team.gdsc.code2cv.feature.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.user.dto.UserRes;
import team.gdsc.code2cv.feature.user.service.UserService;
import team.gdsc.code2cv.global.jwt.JwtUser;

@Tag(name = "User", description = "유저 관련 API")
@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@Operation(summary = "유저 정보 조회", description = "내 정보 리턴")
	@GetMapping("/api/user")
	public UserRes.UserDto getUserInfo(@AuthenticationPrincipal JwtUser jwtUser) {
		return userService.getUser(jwtUser.getId());
	}

}

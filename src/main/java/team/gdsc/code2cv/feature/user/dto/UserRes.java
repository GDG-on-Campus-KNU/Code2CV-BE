package team.gdsc.code2cv.feature.user.dto;

import lombok.Builder;
import team.gdsc.code2cv.feature.user.entity.User;

public class UserRes {
	@Builder
	public record UserDto(
		Long id
	){
		public static UserDto from(User user) {
			return UserDto.builder()
				.id(user.getId())
				.build();
		}
	}
}

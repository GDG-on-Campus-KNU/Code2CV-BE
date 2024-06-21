package team.gdsc.code2cv.feature.user.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import team.gdsc.code2cv.feature.user.entity.User;

public class UserRes {
	@Builder
	public record UserDto(
		Long id,
		String githubUsername,
		String githubName,
		String githubBio,
		String githubCompany,
		LocalDateTime githubStartAt,
		String profileImageUrl
	){
		public static UserDto from(User user) {
			return UserDto.builder()
				.id(user.getId())
				.githubUsername(user.getGithubAccount().getGithubUsername())
				.githubName(user.getGithubAccount().getGithubName())
				.githubBio(user.getGithubAccount().getGithubBio())
				.githubCompany(user.getGithubAccount().getGithubCompany())
				.githubStartAt(user.getGithubAccount().getGithubCreatedAt())
				.profileImageUrl("https://github.com/" + user.getGithubAccount().getGithubUsername() + ".png")
				.build();
		}
	}
}

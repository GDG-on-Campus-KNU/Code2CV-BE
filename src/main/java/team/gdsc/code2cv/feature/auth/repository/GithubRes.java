package team.gdsc.code2cv.feature.auth.repository;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import team.gdsc.code2cv.feature.user.entity.GithubAccount;

public class GithubRes {
	public record GithubAccountDto(
		Long id,
		String login, //github username
		String name, //github name
		String bio,
		String company,
		@JsonProperty("public_repos")
		Integer publicRepos,
		Integer followers,
		Integer following,
		@JsonProperty("created_at")
		String createdAt,
		@JsonProperty("updated_at")
		String updatedAt
	) {
		public GithubAccount toGithubAccount(String accessToken) {
			//2021-01-29T13:38:28Z to ZonedDateTime
			var createdAt = ZonedDateTime.parse(this.createdAt);
			var updatedAt = ZonedDateTime.parse(this.updatedAt);
			return GithubAccount.builder()
				.githubId(String.valueOf(id))
				.githubAccessToken(accessToken)
				.githubUsername(login)
				.githubName(name)
				.githubBio(bio)
				.githubCompany(company)
				.githubPublicReposCount(publicRepos)
				.githubFollowersCount(followers)
				.githubFollowingCount(following)
				.githubCreatedAt(createdAt)
				.githubUpdatedAt(updatedAt)
				.build();
		}
	}
}

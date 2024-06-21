package team.gdsc.code2cv.feature.auth.repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.annotation.JsonProperty;

import team.gdsc.code2cv.feature.user.domain.GithubAccount;

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
		public GithubAccount toDomain(String accessToken) {
			//2021-01-29T13:38:28Z to LocalDateTime
			Instant instantCreated = Instant.parse(this.createdAt);
			Instant instantUpdated = Instant.parse(this.createdAt);

			LocalDateTime createdAt = LocalDateTime.ofInstant(instantCreated, ZoneId.systemDefault());
			LocalDateTime updatedAt = LocalDateTime.ofInstant(instantUpdated, ZoneId.systemDefault());

			return GithubAccount.builder()
				.githubId(id)
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

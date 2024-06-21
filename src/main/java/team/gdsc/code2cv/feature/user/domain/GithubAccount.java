package team.gdsc.code2cv.feature.user.domain;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Embeddable
@Builder
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class GithubAccount {
	private String githubId;
	private String githubAccessToken;
	private String githubUsername;
	private String githubName;
	private String githubBio;
	private String githubCompany;
	private Integer githubPublicReposCount;
	private Integer githubFollowersCount;
	private Integer githubFollowingCount;
	private LocalDateTime githubCreatedAt;
	private LocalDateTime githubUpdatedAt;
}

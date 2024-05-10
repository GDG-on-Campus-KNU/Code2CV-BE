package team.gdsc.code2cv.feature.project.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubCommitInfoModel(
	String url,
	String sha,
	@JsonProperty("node_id") String nodeId,
	@JsonProperty("html_url") String htmlUrl,
	@JsonProperty("comments_url") String commentsUrl,
	Commit commit,
	Author author,
	Committer committer,
	List<Parent> parents
) {
	public record Commit(
		String url,
		Person author,
		Person committer,
		String message,
		Tree tree,
		@JsonProperty("comment_count") int commentCount,
		Verification verification
	) {
		public record Person(
			String name,
			String email,
			LocalDateTime date
		) {
		}

		public record Tree(
			String url,
			String sha
		) {
		}

		public record Verification(
			boolean verified,
			String reason,
			String signature,
			String payload
		) {
		}
	}

	public record Author(
		String login,
		long id,
		@JsonProperty("node_id") String nodeId,
		@JsonProperty("avatar_url") String avatarUrl,
		@JsonProperty("gravatar_id") String gravatarId,
		String url,
		@JsonProperty("html_url") String htmlUrl,
		@JsonProperty("followers_url") String followersUrl,
		@JsonProperty("following_url") String followingUrl,
		@JsonProperty("gists_url") String gistsUrl,
		@JsonProperty("starred_url") String starredUrl,
		@JsonProperty("subscriptions_url") String subscriptionsUrl,
		@JsonProperty("organizations_url") String organizationsUrl,
		@JsonProperty("repos_url") String reposUrl,
		@JsonProperty("events_url") String eventsUrl,
		@JsonProperty("received_events_url") String receivedEventsUrl,
		String type,
		@JsonProperty("site_admin") boolean siteAdmin
	) {
	}

	public record Committer(
		String login,
		long id,
		@JsonProperty("node_id") String nodeId,
		@JsonProperty("avatar_url") String avatarUrl,
		@JsonProperty("gravatar_id") String gravatarId,
		String url,
		@JsonProperty("html_url") String htmlUrl,
		@JsonProperty("followers_url") String followersUrl,
		@JsonProperty("following_url") String followingUrl,
		@JsonProperty("gists_url") String gistsUrl,
		@JsonProperty("starred_url") String starredUrl,
		@JsonProperty("subscriptions_url") String subscriptionsUrl,
		@JsonProperty("organizations_url") String organizationsUrl,
		@JsonProperty("repos_url") String reposUrl,
		@JsonProperty("events_url") String eventsUrl,
		@JsonProperty("received_events_url") String receivedEventsUrl,
		String type,
		@JsonProperty("site_admin") boolean siteAdmin
	) {
	}

	public record Parent(
		String url,
		String sha
	) {
	}
}

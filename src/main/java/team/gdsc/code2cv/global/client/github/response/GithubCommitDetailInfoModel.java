package team.gdsc.code2cv.global.client.github.response;

import java.time.LocalDateTime;
import java.util.List;

public record GithubCommitDetailInfoModel(
	String sha,
	String node_id,
	Commit commit,
	String url,
	String html_url,
	String comments_url,
	Author author,
	Committer committer,
	List<Parent> parents,
	Stats stats,
	List<File> files
) {
	public record Commit(
		Author author,
		Committer committer,
		String message,
		Tree tree,
		int comment_count,
		Verification verification
	) {
		public record Author(
			String name,
			String email,
			LocalDateTime date
		) {}

		public record Committer(
			String name,
			String email,
			LocalDateTime date
		) {}

		public record Tree(
			String sha,
			String url
		) {}

		public record Verification(
			boolean verified,
			String reason,
			String signature,
			String payload
		) {}
	}

	public record Author(
		String login,
		long id,
		String node_id,
		String avatar_url,
		String gravatar_id,
		String url,
		String html_url,
		String followers_url,
		String following_url,
		String gists_url,
		String starred_url,
		String subscriptions_url,
		String organizations_url,
		String repos_url,
		String events_url,
		String received_events_url,
		String type,
		boolean site_admin
	) {}

	public record Committer(
		String login,
		long id,
		String node_id,
		String avatar_url,
		String gravatar_id,
		String url,
		String html_url,
		String followers_url,
		String following_url,
		String gists_url,
		String starred_url,
		String subscriptions_url,
		String organizations_url,
		String repos_url,
		String events_url,
		String received_events_url,
		String type,
		boolean site_admin
	) {}

	public record Parent(
		String sha,
		String url,
		String html_url
	) {}

	public record Stats(
		int total,
		int additions,
		int deletions
	) {}

	public record File(
		String sha,
		String filename,
		String status,
		int additions,
		int deletions,
		int changes,
		String blob_url,
		String raw_url,
		String contents_url,
		String patch
	) {}
}

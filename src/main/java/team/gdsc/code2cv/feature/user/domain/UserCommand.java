package team.gdsc.code2cv.feature.user.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import team.gdsc.code2cv.core.utils.SelfValidating;


public class UserCommand {

	@Getter
	@Builder
	public static class CreateByGithub extends SelfValidating<UserCommand.CreateByGithub> {
		@NotNull
		private final GithubAccount githubAccount;

		public CreateByGithub(GithubAccount githubAccount) {
			this.githubAccount = githubAccount;
			validateSelf();
		}
	}

	@Getter
	@Builder
	public static class CreateByEmail extends SelfValidating<UserCommand.CreateByEmail> {
		@Email(message = "이메일 형식이 아닙니다.")
		private final String email;
		@NotBlank(message = "비밀번호는 필수입니다.")
		private final String password;
		@NotNull
		public final GithubAccount githubAccount;

		public CreateByEmail(String email, String password, GithubAccount githubAccount) {
			this.email = email;
			this.password = password;
			this.githubAccount = githubAccount;
			validateSelf();
		}
	}

}

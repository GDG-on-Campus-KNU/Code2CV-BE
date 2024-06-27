package team.gdsc.code2cv.feature.resume.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import team.gdsc.code2cv.core.utils.SelfValidating;

public class ResumeCommand {

	@Getter
	@Builder
	public static class CreateByNew extends SelfValidating<CreateByNew> {
		@NotBlank(message = "이름은 필수입니다.")
		private final String name;

		@NotNull(message = "isDone은 필수입니다.")
		private final Boolean isDone;

		public CreateByNew(String name, Boolean isDone) {
			if (name != null)
				this.name = name.strip();
			else
				this.name = null;
			this.isDone = isDone;
			this.validateSelf();
		}
	}

	@Getter
	@Builder
	public static class CreateByUpload extends SelfValidating<CreateByUpload> {
		@NotBlank(message = "이름은 필수입니다.")
		private final String name;

		@NotBlank(message = "파일은 필수입니다.")
		// TODO: 파일 업로드 처리
		private final String file;

		public CreateByUpload(String name, String file) {
			if (name != null)
				this.name = name.strip();
			else
				this.name = null;
			this.file = file;
			this.validateSelf();
		}
	}
}

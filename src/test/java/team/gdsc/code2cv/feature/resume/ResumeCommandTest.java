package team.gdsc.code2cv.feature.resume;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolationException;
import team.gdsc.code2cv.feature.resume.domain.ResumeCommand;

@SpringBootTest
public class ResumeCommandTest {

	@Nested
	class CreateByNewTest {

		@Test
		@DisplayName("모든 필드가 정상인 경우")
		void success() {
			// given
			String name = "홍길동";
			Boolean isDone = true;

			// when
			ResumeCommand.CreateByNew command = ResumeCommand.CreateByNew.builder()
				.name(name)
				.isDone(isDone)
				.build();

			// then
			assertThat(command.getName()).isEqualTo(name);
			assertThat(command.getIsDone()).isEqualTo(isDone);
		}

		@Test
		@DisplayName("이름이 null인 경우")
		void nameIsNull() {
			// given
			Boolean isDone = true;

			// when & then
			assertThatThrownBy(() -> ResumeCommand.CreateByNew.builder()
				.name(null)
				.isDone(isDone)
				.build())
				.isInstanceOf(ConstraintViolationException.class)
				.hasMessageContaining("이름은 필수입니다.");
		}

		@Test
		@DisplayName("이름이 빈 문자열인 경우")
		void nameIsEmpty() {
			// given
			String name = "  ";
			Boolean isDone = true;

			// when & then
			assertThatThrownBy(() -> ResumeCommand.CreateByNew.builder()
				.name(name)
				.isDone(isDone)
				.build())
				.isInstanceOf(ConstraintViolationException.class)
				.hasMessageContaining("이름은 필수입니다.");
		}

		@Test
		@DisplayName("isDone이 null인 경우")
		void isDoneIsNull() {
			// given
			String name = "홍길동";
			Boolean isDone = null;

			// when & then
			assertThatThrownBy(() -> ResumeCommand.CreateByNew.builder()
				.name(name)
				.isDone(isDone)
				.build())
				.isInstanceOf(ConstraintViolationException.class)
				.hasMessageContaining("isDone은 필수입니다.");
		}
	}

	@Nested
	class CreateByUploadTest {

		@Test
		@DisplayName("모든 필드가 정상인 경우")
		void success() {
			// given
			String name = "홍길동";
			String file = "file";

			// when
			ResumeCommand.CreateByUpload command = ResumeCommand.CreateByUpload.builder()
				.name(name)
				.file(file)
				.build();

			// then
			assertThat(command.getName()).isEqualTo(name);
			assertThat(command.getFile()).isEqualTo(file);
		}

		@Test
		@DisplayName("이름이 null인 경우")
		void nameIsNull() {
			// given
			String file = "file";

			// when & then
			assertThatThrownBy(() -> ResumeCommand.CreateByUpload.builder()
				.name(null)
				.file(file)
				.build())
				.isInstanceOf(ConstraintViolationException.class)
				.hasMessageContaining("이름은 필수입니다.");
		}

		@Test
		@DisplayName("이름이 빈 문자열인 경우")
		void nameIsEmpty() {
			// given
			String name = "  ";
			String file = "file";

			// when & then
			assertThatThrownBy(() -> ResumeCommand.CreateByUpload.builder()
				.name(name)
				.file(file)
				.build())
				.isInstanceOf(ConstraintViolationException.class)
				.hasMessageContaining("이름은 필수입니다.");
		}

		@Test
		@DisplayName("파일이 null인 경우")
		void fileIsNull() {
			// given
			String name = "홍길동";

			// when & then
			assertThatThrownBy(() -> ResumeCommand.CreateByUpload.builder()
				.name(name)
				.file(null)
				.build())
				.isInstanceOf(ConstraintViolationException.class)
				.hasMessageContaining("파일은 필수입니다.");
		}

		@Test
		@DisplayName("파일이 빈 문자열인 경우")
		void fileIsEmpty() {
			// given
			String name = "홍길동";
			String file = "  ";

			// when & then
			assertThatThrownBy(() -> ResumeCommand.CreateByUpload.builder()
				.name(name)
				.file(file)
				.build())
				.isInstanceOf(ConstraintViolationException.class)
				.hasMessageContaining("파일은 필수입니다.");
		}
	}
}

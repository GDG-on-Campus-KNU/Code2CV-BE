package team.gdsc.code2cv.feature.resume;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import team.gdsc.code2cv.feature.resume.domain.ResumeCommand;
import team.gdsc.code2cv.feature.resume.dto.ResumeRes;
import team.gdsc.code2cv.feature.resume.entity.Resume;
import team.gdsc.code2cv.feature.resume.repository.ResumeRepository;
import team.gdsc.code2cv.feature.resume.service.ResumeService;
import team.gdsc.code2cv.feature.user.domain.GithubAccount;
import team.gdsc.code2cv.feature.user.domain.UserCommand;
import team.gdsc.code2cv.feature.user.entity.User;
import team.gdsc.code2cv.feature.user.repository.UserRepository;

@SpringBootTest
public class ResumeServiceTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ResumeService resumeService;
	@Autowired
	private ResumeRepository resumeRepository;

	@Nested
	@Transactional
	class GetAllResumes {

		@Test
		@DisplayName("유저의 모든 이력서를 조회한다.")
		void getAllResumes() {
			// given
			// User
			UserCommand.CreateByEmail userCommand = new UserCommand.CreateByEmail("test@abc.def", "password",
				new GithubAccount());

			User user = userRepository.save(User.create(userCommand));

			// Resumes
			List<Resume> resumes = new ArrayList<>();

			var newCommands = List.of(
				ResumeCommand.CreateByNew.builder()
					.name("이력서")
					.isDone(false)
					.build(),
				ResumeCommand.CreateByNew.builder()
					.name("이력서2")
					.isDone(false)
					.build(),
				ResumeCommand.CreateByNew.builder()
					.name("이력서3")
					.isDone(false)
					.build()
			);

			var uploadCommand = ResumeCommand.CreateByUpload.builder()
				.name("이력서4")
				.file("파일")
				.build();

			newCommands.stream()
				.map(command -> Resume.create(command, user))
				.map(resumeRepository::save)
				.forEach(resumes::add);

			resumeRepository.save(Resume.create(uploadCommand, user));

			// when
			List<ResumeRes.ResumeDto> fetchResumes = resumeService.getAllResumes(user.getId());

			// then
			assertThat(fetchResumes).hasSize(4);
			assertThat(fetchResumes.get(0).name()).isEqualTo("이력서");
			assertThat(fetchResumes.get(1).name()).isEqualTo("이력서2");
			assertThat(fetchResumes.get(2).name()).isEqualTo("이력서3");
			assertThat(fetchResumes.get(3).name()).isEqualTo("이력서4");

			assertThat(fetchResumes.get(0).isDone()).isFalse();
			assertThat(fetchResumes.get(1).isDone()).isFalse();
			assertThat(fetchResumes.get(2).isDone()).isFalse();
			assertThat(fetchResumes.get(3).isDone()).isTrue();

			assertThat(fetchResumes.get(0).isDefault()).isFalse();
			assertThat(fetchResumes.get(1).isDefault()).isFalse();
			assertThat(fetchResumes.get(2).isDefault()).isFalse();
			assertThat(fetchResumes.get(3).isDefault()).isFalse();

			assertThat(fetchResumes.get(0).file()).isNull();
			assertThat(fetchResumes.get(1).file()).isNull();
			assertThat(fetchResumes.get(2).file()).isNull();
			assertThat(fetchResumes.get(3).file()).isEqualTo("파일");
		}

		@Test
		@DisplayName("유저의 이력서가 없을 때 빈 리스트를 반환한다.")
		void getAllResumes_empty() {
			// given
			// User
			UserCommand.CreateByEmail userCommand = new UserCommand.CreateByEmail("test@abc.def", "password",
				new GithubAccount());

			User user = userRepository.save(User.create(userCommand));

			// when
			List<ResumeRes.ResumeDto> resumes = resumeService.getAllResumes(user.getId());

			// then
			assertThat(resumes).isEmpty();
		}
	}

	@Nested
	@Transactional
	class GetResumeTest {

		@Test
		@DisplayName("유저의 이력서를 조회한다.")
		void getResume() {
			// given
			// User
			UserCommand.CreateByEmail userCommand = new UserCommand.CreateByEmail("test@abc.def", "password",
				new GithubAccount());

			User user = userRepository.save(User.create(userCommand));

			// Resumes
			List<Resume> resumes = new ArrayList<>();

			var newCommands = List.of(
				ResumeCommand.CreateByNew.builder()
					.name("이력서")
					.isDone(false)
					.build(),
				ResumeCommand.CreateByNew.builder()
					.name("이력서2")
					.isDone(false)
					.build(),
				ResumeCommand.CreateByNew.builder()
					.name("이력서3")
					.isDone(false)
					.build()
			);

			var uploadCommand = ResumeCommand.CreateByUpload.builder()
				.name("이력서4")
				.file("파일")
				.build();

			newCommands.stream()
				.map(command -> Resume.create(command, user))
				.map(resumeRepository::save)
				.forEach(resumes::add);

			resumeRepository.save(Resume.create(uploadCommand, user));

			// when
			ResumeRes.ResumeDetailDto resume = resumeService.getResume(user.getId(), resumes.get(0).getId());

			// then
			assertThat(resume.name()).isEqualTo("이력서");
			assertThat(resume.isDone()).isFalse();
			assertThat(resume.isDefault()).isFalse();
			assertThat(resume.file()).isNull();
		}

		@Test
		@DisplayName("유저의 이력서가 없을 때 예외를 발생시킨다.")
		void getResume_notFound() {
			// given
			// User
			UserCommand.CreateByEmail userCommand = new UserCommand.CreateByEmail("test@abc.def", "password",
				new GithubAccount());

			User user = userRepository.save(User.create(userCommand));

			// when, then
			assertThatThrownBy(() -> resumeService.getResume(user.getId(), 1L))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("해당 이력서를 찾을 수 없습니다.");
		}

		@Test
		@DisplayName("유저의 이력서가 아닐 때 예외를 발생시킨다.")
		void getResume_notUser() {
			// given
			// User
			UserCommand.CreateByEmail userCommand = new UserCommand.CreateByEmail("test@abc.def", "password",
				new GithubAccount());

			User user = userRepository.save(User.create(userCommand));

			// Resumes
			List<Resume> resumes = new ArrayList<>();

			var newCommands = List.of(
				ResumeCommand.CreateByNew.builder()
					.name("이력서")
					.isDone(false)
					.build(),
				ResumeCommand.CreateByNew.builder()
					.name("이력서2")
					.isDone(false)
					.build(),
				ResumeCommand.CreateByNew.builder()
					.name("이력서3")
					.isDone(false)
					.build()
			);

			var uploadCommand = ResumeCommand.CreateByUpload.builder()
				.name("이력서4")
				.file("파일")
				.build();

			newCommands.stream()
				.map(command -> Resume.create(command, user))
				.map(resumeRepository::save)
				.forEach(resumes::add);

			resumeRepository.save(Resume.create(uploadCommand, user));

			UserCommand.CreateByEmail notUserCommand = new UserCommand.CreateByEmail("not_user@abc.def", "password",
				new GithubAccount());

			User notUser = userRepository.save(User.create(userCommand));

			// when, then
			assertThatThrownBy(() -> resumeService.getResume(notUser.getId(), 1L))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("해당 이력서를 찾을 수 없습니다.");
		}
	}

}

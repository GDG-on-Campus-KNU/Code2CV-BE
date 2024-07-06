package team.gdsc.code2cv.feature.resume;

import static org.assertj.core.api.Assertions.*;

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
import team.gdsc.code2cv.feature.user.domain.Role;
import team.gdsc.code2cv.global.jwt.JwtUser;

@SpringBootTest
public class ResumeServiceTest {

	@Autowired private ResumeService resumeService;
	@Autowired private ResumeRepository resumeRepository;

	@Nested
	@Transactional
	class GetAllResumes {

		@Test
		@DisplayName("유저의 모든 이력서를 조회한다.")
		void getAllResumes() {
			// given
			var commands = List.of(
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
					.build(),
				ResumeCommand.CreateByUpload.builder()
					.name("이력서4")
					.file("파일")
					.build()
			);

			JwtUser jwtUser = JwtUser.builder()
				.id(1L)
				.role(Role.USER)
				.build();

			commands.forEach(command -> {
				Resume resume = null;
				if (command instanceof ResumeCommand.CreateByNew) {
					resume = Resume.create((ResumeCommand.CreateByNew) command, jwtUser);
				} else if (command instanceof ResumeCommand.CreateByUpload) {
					resume = Resume.create((ResumeCommand.CreateByUpload) command, jwtUser);
				} else {
					throw new IllegalArgumentException("지원하지 않는 타입입니다.");
				}
				resumeRepository.save(resume);
			});

			// when
			List<ResumeRes.ResumeDto> resumes = resumeService.getAllResumes(jwtUser);

			// then
			assertThat(resumes).hasSize(4);
			assertThat(resumes.get(0).name()).isEqualTo("이력서");
			assertThat(resumes.get(1).name()).isEqualTo("이력서2");
			assertThat(resumes.get(2).name()).isEqualTo("이력서3");
			assertThat(resumes.get(3).name()).isEqualTo("이력서4");

			assertThat(resumes.get(0).isDone()).isFalse();
			assertThat(resumes.get(1).isDone()).isFalse();
			assertThat(resumes.get(2).isDone()).isFalse();
			assertThat(resumes.get(3).isDone()).isTrue();

			assertThat(resumes.get(0).isDefault()).isFalse();
			assertThat(resumes.get(1).isDefault()).isFalse();
			assertThat(resumes.get(2).isDefault()).isFalse();
			assertThat(resumes.get(3).isDefault()).isFalse();

			assertThat(resumes.get(0).file()).isNull();
			assertThat(resumes.get(1).file()).isNull();
			assertThat(resumes.get(2).file()).isNull();
			assertThat(resumes.get(3).file()).isEqualTo("파일");
		}

		@Test
		@DisplayName("유저의 이력서가 없을 때 빈 리스트를 반환한다.")
		void getAllResumes_empty() {
			// given
			JwtUser jwtUser = JwtUser.builder()
				.id(1L)
				.role(Role.USER)
				.build();

			// when
			List<ResumeRes.ResumeDto> resumes = resumeService.getAllResumes(jwtUser);

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
			var commands = List.of(
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
					.build(),
				ResumeCommand.CreateByUpload.builder()
					.name("이력서4")
					.file("파일")
					.build()
			);

			JwtUser jwtUser = JwtUser.builder()
				.id(1L)
				.role(Role.USER)
				.build();


			List<Resume> resumes = commands.stream()
					.map(command -> {
						Resume resume = null;
						if (command instanceof ResumeCommand.CreateByNew) {
							resume = Resume.create((ResumeCommand.CreateByNew) command, jwtUser);
						} else if (command instanceof ResumeCommand.CreateByUpload) {
							resume = Resume.create((ResumeCommand.CreateByUpload) command, jwtUser);
						} else {
							throw new IllegalArgumentException("지원하지 않는 타입입니다.");
						}
						return resume;
					}).map(resumeRepository::save).toList();

			// when
			ResumeRes.ResumeDetailDto resume = resumeService.getResume(jwtUser, resumes.get(0).getId());

			System.out.println("resume = " + resume);

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
			JwtUser jwtUser = JwtUser.builder()
				.id(1L)
				.role(Role.USER)
				.build();

			// when, then
			assertThatThrownBy(() -> resumeService.getResume(jwtUser, 1L))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("해당 이력서를 찾을 수 없습니다.");
		}

		@Test
		@DisplayName("유저의 이력서가 아닐 때 예외를 발생시킨다.")
		void getResume_notUser() {
			// given
			var commands = List.of(
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
					.build(),
				ResumeCommand.CreateByUpload.builder()
					.name("이력서4")
					.file("파일")
					.build()
			);

			JwtUser jwtUser = JwtUser.builder()
				.id(1L)
				.role(Role.USER)
				.build();

			commands.forEach(command -> {
				Resume resume = null;
				if (command instanceof ResumeCommand.CreateByNew) {
					resume = Resume.create((ResumeCommand.CreateByNew) command, jwtUser);
				} else if (command instanceof ResumeCommand.CreateByUpload) {
					resume = Resume.create((ResumeCommand.CreateByUpload) command, jwtUser);
				} else {
					throw new IllegalArgumentException("지원하지 않는 타입입니다.");
				}
				resumeRepository.save(resume);
			});

			JwtUser notUser = JwtUser.builder()
				.id(2L)
				.role(Role.USER)
				.build();

			// when, then
			assertThatThrownBy(() -> resumeService.getResume(notUser, 1L))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("해당 이력서를 찾을 수 없습니다.");
		}
	}

}

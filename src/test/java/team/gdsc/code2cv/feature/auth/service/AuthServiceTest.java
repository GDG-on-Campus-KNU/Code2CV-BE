package team.gdsc.code2cv.feature.auth.service;



import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import jakarta.persistence.EntityManager;
import team.gdsc.code2cv.feature.auth.dto.AuthReq;
import team.gdsc.code2cv.feature.auth.dto.AuthRes;
import team.gdsc.code2cv.feature.auth.repository.GithubClient;
import team.gdsc.code2cv.feature.user.domain.GithubAccount;
import team.gdsc.code2cv.feature.user.domain.UserCommand;
import team.gdsc.code2cv.feature.user.entity.User;
import team.gdsc.code2cv.feature.user.repository.UserRepository;

@SpringBootTest
@Import(AuthServiceTest.AuthServiceTestConfig.class)
class AuthServiceTest {
	@Autowired private AuthService authService;
	@Autowired private UserRepository userRepository;
	@Autowired private EntityManager em;


	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

	@DisplayName("OAuth2 회원가입이 정상적으로 동작한다.")
	@Test
	void githubLoginOrSignUp() {
		// given
		AuthReq.GithubLoginRequest req = new AuthReq.GithubLoginRequest("fake-random-code", "fake-random-state");

		// when
		AuthRes.LoginResponse loginResponse = authService.githubLoginOrSignUp(req);
		em.clear();

		// then
		User user = userRepository.findAll().get(0);
		assertThat(user.getId()).isEqualTo(loginResponse.user().id());
	}

	@DisplayName("이미 가입된 유저가 로그인을 시도할경우, 로그인이 정상적으로 동작한다.")
	@Test
	void githubLoginOrSignUpWhenAlreadySignedUp() {
		// given
		AuthReq.GithubLoginRequest req = new AuthReq.GithubLoginRequest("fake", "random");
		authService.githubLoginOrSignUp(req);
		em.clear();

		// when
		AuthRes.LoginResponse loginResponse = authService.githubLoginOrSignUp(req);
		em.clear();

		// then
		User user = userRepository.findAll().get(0);
		assertThat(user.getId()).isEqualTo(loginResponse.user().id());
	}


	@DisplayName("이메일 회원가입이 정상적으로 동작한다.")
	@Test
	void emailSignUp() {
		// given
		AuthReq.EmailSignUpRequest req = new AuthReq.EmailSignUpRequest("test@naver.com", "fake-git-token", "fake-token");

		// when
		authService.emailSignUp(req);
		em.clear();

		// then
		User user = userRepository.findAll().get(0);
		assertThat(user.getEmail()).isEqualTo(req.email());
		assertThat(user.getPassword()).isNotEqualTo(req.password());
	}

	@DisplayName("이미 등록된 깃허브 계정이 존재할때, 이메일 회원가입으로 같은 유저의 깃허브 계정을 등록할 수 없다.")
	@Test
	void emailSignUpWhenAlreadySignedUpGithubId() {
		// given
		AuthReq.EmailSignUpRequest req = new AuthReq.EmailSignUpRequest("test@a.c", "fake-git-token", "fake-token");
		UserCommand.CreateByGithub command = UserCommand.CreateByGithub.builder()
			.githubAccount(genGithubAccount())
			.build();
		User user = User.create(command);
		userRepository.save(user);
		em.clear();

		// when, then
		assertThrows(IllegalArgumentException.class, () -> authService.emailSignUp(req));
		List<User> users = userRepository.findAll();
		assertThat(users.size()).isEqualTo(1);

	}

	@DisplayName("이메일 회원가입시, 이미 등록된 이메일로 가입할 수 없다.")
	@Test
	void emailSignUpWhenAlreadySignedUp() {
		// given
		AuthReq.EmailSignUpRequest req = new AuthReq.EmailSignUpRequest("test@a.c", "fake-git-token", "fake-token");
		UserCommand.CreateByGithub command = UserCommand.CreateByGithub.builder()
			.githubAccount(genGithubAccount())
			.build();
		User user = User.create(command);
		userRepository.save(user);
		em.clear();

		// when, then
		assertThrows(IllegalArgumentException.class, () -> authService.emailSignUp(req));
		List<User> users = userRepository.findAll();
		assertThat(users.size()).isEqualTo(1);
	}


	@DisplayName("이메일 로그인이 정상적으로 동작한다.")
	@Test
	void emailLogin() {
		// given
		AuthReq.EmailLoginRequest req = new AuthReq.EmailLoginRequest("test@naver.com", "password");
		authService.emailSignUp(new AuthReq.EmailSignUpRequest(req.email(), req.password(), "fake-token"));

		// when
		AuthRes.LoginResponse loginResponse = authService.emailLogin(req);
		em.clear();

		// then
		User user = userRepository.findAll().get(0);
		assertThat(user.getId()).isEqualTo(loginResponse.user().id());
	}

	private GithubAccount genGithubAccount() {
		return GithubAccount.builder()
			.githubId(1L)
			.githubCompany("FAKE_COMPANY")
			.githubName("FAKE_NAME")
			.githubBio("FAKE_BIO")
			.githubUsername("FAKE_USERNAME")
			.githubAccessToken("FAKE_ACCESS")
			.githubFollowersCount(1)
			.githubFollowingCount(1)
			.githubPublicReposCount(2)
			.githubCreatedAt(LocalDateTime.now())
			.githubUpdatedAt(LocalDateTime.now())
			.build();
	}


	@TestConfiguration
	static class AuthServiceTestConfig {
		@Bean
		@Primary
		public GithubClient githubClient() {
			return new FakeGithubClient();
		}
	}

	static class FakeGithubClient implements GithubClient {
		@Override
		public String getGithubAccessToken(String code, String state) {
			return "FAKE_ACCESS_TOKEN";
		}

		@Override
		public GithubAccount getGithubAccountResponse(String accessToken) {
			return GithubAccount.builder()
				.githubId(1L)
				.githubCompany("FAKE_COMPANY")
				.githubName("FAKE_NAME")
				.githubBio("FAKE_BIO")
				.githubUsername("FAKE_USERNAME")
				.githubAccessToken("FAKE_ACCESS")
				.githubFollowersCount(1)
				.githubFollowingCount(1)
				.githubPublicReposCount(2)
				.githubCreatedAt(LocalDateTime.now())
				.githubUpdatedAt(LocalDateTime.now())
				.build();
		}
	}
}
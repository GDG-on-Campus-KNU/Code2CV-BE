package team.gdsc.code2cv.feature.project.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GithubRepositoryClientTest {
	@Autowired
	private GithubRepositoryClient githubRepositoryClient;
	private final String token = null;
	private final String owner = null;
	private final String repo = null;

	@DisplayName("스프링에서 api호출해보기 테스트, 실제 테스트에서는 절대 사용하지 말 것")
	@Test
	void getRepositoryList() {
		if (token != null) {
			var list = githubRepositoryClient.getAllRepositoriesInfo(token);
			list.forEach(System.out::println);
		}
	}

	@DisplayName("스프링에서 api호출해보기 테스트, 실제 테스트에서는 절대 사용하지 말 것")
	@Test
	void getCommitList() {
		if (token != null) {

		}
	}
}

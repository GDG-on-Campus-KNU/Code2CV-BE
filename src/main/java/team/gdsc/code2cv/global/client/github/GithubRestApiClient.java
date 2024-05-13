package team.gdsc.code2cv.global.client.github;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import team.gdsc.code2cv.feature.project.dto.response.GithubRepositoryInfoModel;

@HttpExchange
public interface GithubRestApiClient {

	@GetExchange("/user/repos")
	ResponseEntity<List<GithubRepositoryInfoModel>> getRepositoryList(
		@RequestHeader("Authorization") String token, // Bearer token
		@RequestParam(value = "page", defaultValue = "1") int page, // 1, 2, 3, ...
		@RequestParam(value = "per_page", defaultValue = "30") int perPage,
		@RequestParam(value = "sort", defaultValue = "updated") String sort, // created, updated, pushed, full_name
		@RequestParam(value = "since", required = false) String since,
		@RequestParam(value = "before", required = false) String before,
		@RequestParam(value = "visibility", defaultValue = "public") String visibility,
		@RequestParam(value = "affiliation", defaultValue = "owner,collaborator") String affiliation
	);
}

package team.gdsc.code2cv.feature.projectAnalysis.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AnalysisAIClient {


	public AnalysisAIRequest analyze(List<String> request) {
		return new AnalysisAIRequest(
			"Service: 3, Repository: 3, Controller: 3, Entity: 3, DTO: 3",
			"Java: 3, html: 3, css: 3, javascript: 3",
			"Spring Boot, JPA, MySQL",
			"[{content:유저 CRUD, ratio:3}, {content:게시판 CRUD, ratio:3}, {content:채팅 CRUD, ratio:3}]",
			"로그인 화면에 event에 onChange 함수로 input값을 모두 관리하고 있습니다. useRef나, 디바운스를 이용하면 최적화가 가능합니다. useEffect로 상태를 매번 추적하므로, 리렌더링이 계속 진행됩니다.\n"
				+ "비밀번호 상태를 매번 추적하므로, 허가된 비밀번호는 즉시 파란색으로 표시됩니다.\n"
				+ "비밀번호 validation은 객체를 사용하면 O(1) 시간 복잡도로 해결가능합니다.\n"
				+ "constant 폴더에서 상수를 따로 관리할 수 있습니다.\n"
				+ "depth가 2이상입니다. eslint 설정으로 depth 제한을 설정할 수 있습니다.\n"
				+ "\n"
				+ "woff 폰트가 Global style로 설정되어 있습니다.\n"
				+ "font-display: swap 폰트 최적화가 필요합니다.\n"
				+ "디바이스에 시스템 폰트가 없는 경우, 로딩까지 폰트가 보여지지 않을 수 있습니다.\n"
				+ "\n"
				+ "이미지 최적화가 필요합니다.\n"
			);
	}


	public record AnalysisAIRequest(
		String contribution,
		String language,
		String techStack,
		String implementedFunction,
		String ImprovementPlan
	) {
	}
}

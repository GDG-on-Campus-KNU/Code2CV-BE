package team.gdsc.code2cv.feature.user.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class GithubAccount {
	private String githubId;
	private String githubAccessToken;
	//TODO
}

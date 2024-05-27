package team.gdsc.code2cv.global.client.oauth2;

import team.gdsc.code2cv.global.client.github.response.GithubUserInfoModel;

public record Oauth2UserModel(
	String userToken
) {
	static public Oauth2UserModel from(GithubUserInfoModel githubUserInfoModel) {
		return new Oauth2UserModel("Github_" + githubUserInfoModel.id());
	}
}

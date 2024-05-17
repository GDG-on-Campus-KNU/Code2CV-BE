package team.gdsc.code2cv.feature.user.entity;

public enum VendorName {
	GITHUB("github"),
	GOOGLE("google"),
	KAKAO("kakao");

	private final String name;

	VendorName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

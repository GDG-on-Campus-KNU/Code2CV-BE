package team.gdsc.code2cv.feature.user.entity;

public enum VendorName {
	GITHUB("Github"),
	GOOGLE("Google"),
	KAKAO("Kakao");

	private final String name;

	VendorName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static boolean valid(String name) {
		for (VendorName vendorName : VendorName.values()) {
			if (vendorName.name.equals(name)) {
				return true;
			}
		}
		return false;
	}

}

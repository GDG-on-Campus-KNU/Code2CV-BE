package team.gdsc.code2cv.feature.user.domain;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import team.gdsc.code2cv.core.utils.SelfValidating;
import team.gdsc.code2cv.feature.user.entity.VendorName;

@Getter
public class UserAccountCreate extends SelfValidating<UserAccountCreate> {
    /*
    로그인 로직과 깃허브 연동 로직 분리
     */
    @Size(min = 0, message = "아이디는 0자 이상이어야 합니다.")
    private final String VendorEmail;
    @ValidVendorName
    private final String vendorName;

    public UserAccountCreate(String VendorEmail, String vendorName) {
        this.VendorEmail = VendorEmail;
        this.vendorName = vendorName;
        this.validateSelf();
    }
}

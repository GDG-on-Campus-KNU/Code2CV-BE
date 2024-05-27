package team.gdsc.code2cv.feature.user.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.user.domain.UserAccountCreate;
import team.gdsc.code2cv.feature.user.entity.UserAccount;
import team.gdsc.code2cv.feature.user.repository.UserAccountRepository;
import team.gdsc.code2cv.global.jwt.JwtUser;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService {
	private final UserAccountRepository userAccountRepository;

	public JwtUser loadUserByUserToken(String userToken) {
		UserAccount userAccount = userAccountRepository.findByUserToken(userToken)
			.orElseGet(() -> userAccountRepository.save(
				UserAccount.create(UserAccountCreate.builder().userToken(userToken).build())
			));
		return JwtUser.builder().id(userAccount.getId()).role(userAccount.getRole()).build();
	}
}

package team.gdsc.code2cv.feature.user.service;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team.gdsc.code2cv.feature.user.repository.UserAccountRepository;

@Service
@RequiredArgsConstructor
public class UserAccountService {
	private final UserAccountRepository userAccountRepository;
}

package team.gdsc.code2cv.global.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

@Configuration
public class RestClientConfig {

	/**
	 * RestTemplate의 기본 설정을 변경하기 위한 빈을 생성한다.
	 * 타임아웃 시간은 20초로 설정한다.
	 */
	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(20000);
		factory.setReadTimeout(20000);
		return factory;
	}
}

package team.gdsc.code2cv.core.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 두 개의 값을 가지는 Pair 클래스
 * @param <First> 첫 번째 값의 타입
 * @param <Second> 두 번째 값의 타입
 */
@Getter
@RequiredArgsConstructor
public class Pair<First, Second> {
	private final First first;
	private final Second second;
}

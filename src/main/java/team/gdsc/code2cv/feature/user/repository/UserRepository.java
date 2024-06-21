package team.gdsc.code2cv.feature.user.repository;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import team.gdsc.code2cv.feature.user.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

	default User findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(NoSuchElementException::new);
	}
	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	Optional<User> findByGithubAccountGithubId(String githubId);
}

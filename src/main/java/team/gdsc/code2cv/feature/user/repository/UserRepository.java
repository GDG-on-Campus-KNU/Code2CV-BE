package team.gdsc.code2cv.feature.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team.gdsc.code2cv.feature.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

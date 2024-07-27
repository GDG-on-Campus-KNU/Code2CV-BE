package team.gdsc.code2cv.feature.resume.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team.gdsc.code2cv.feature.resume.entity.Resume;
import team.gdsc.code2cv.feature.user.entity.User;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

	List<Resume> findAllByUser(User user);

	Optional<Resume> findByIdAndUser(Long id, User user);
}

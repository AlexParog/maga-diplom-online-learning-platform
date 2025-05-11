package ru.parog.magatestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.parog.magatestservice.entity.Test;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    Optional<Test> findByCourseIdAndLessonId(Long courseId, Long lessonId);

    List<Test> findByCourseId(Long courseId);

    Optional<Test> findByLessonId(Long lessonId);

    boolean existsByLessonId(Long lessonId);
}

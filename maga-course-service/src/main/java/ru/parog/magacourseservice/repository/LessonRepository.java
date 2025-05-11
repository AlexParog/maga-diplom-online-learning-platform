package ru.parog.magacourseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.parog.magacourseservice.entity.Lesson;

import java.util.List;
import java.util.Optional;


@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByModuleIdOrderByOrderIndex(Long moduleId);

    Optional<Integer> findMaxOrderIndexByModuleId(Long moduleId);

    @Modifying
    @Query("UPDATE Lesson l SET l.orderIndex = l.orderIndex - 1 WHERE l.module.id = :moduleId AND l.orderIndex > :orderIndex")
    void decrementOrderIndexForLessonsAfter(Long moduleId, Integer orderIndex);
}

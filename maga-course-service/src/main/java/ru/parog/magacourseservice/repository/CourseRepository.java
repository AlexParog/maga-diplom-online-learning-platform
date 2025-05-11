package ru.parog.magacourseservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.parog.magacourseservice.entity.Course;

import java.util.List;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Course> findByInstructorId(Long instructorId, Pageable pageable);

    Page<Course> findByPublished(boolean published, Pageable pageable);

    @Query("SELECT c FROM Course c WHERE " +
            "(:title IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:instructorId IS NULL OR c.instructorId = :instructorId) AND " +
            "(:published IS NULL OR c.published = :published)")
    Page<Course> findWithFilters(String title, Long instructorId, Boolean published, Pageable pageable);

    List<Course> findByIdIn(List<Long> courseIds);

}

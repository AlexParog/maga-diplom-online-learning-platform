package ru.parog.magacourseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.parog.magacourseservice.entity.Module;

import java.util.List;
import java.util.Optional;


@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    @Query("SELECT m FROM Module m WHERE m.course.id = :courseId ORDER BY m.orderIndex")
    List<Module> findByCourseIdOrderByOrderIndex(Long courseId);

    @Query("SELECT MAX(m.orderIndex) FROM Module m WHERE m.course.id = :courseId")
    Optional<Integer> findMaxOrderIndexByCourseId(Long courseId);

    // Удаление связанных с курсом модулей одним запросом
    @Modifying
    @Query("DELETE FROM Module m WHERE m.course.id = :courseId")
    void deleteByCourseId(Long courseId);

}

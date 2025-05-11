package ru.parog.magatestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.parog.magatestservice.entity.Question;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findByIdAndTestId(Long id, Long testId);

    List<Question> findByTestId(Long testId);

    @Query("SELECT DISTINCT q FROM Question q LEFT JOIN FETCH q.options WHERE q.test.id = :testId ORDER BY q.orderIndex")
    List<Question> findByTestIdWithOptions(Long testId);
}

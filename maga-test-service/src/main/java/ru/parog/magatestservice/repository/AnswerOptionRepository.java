package ru.parog.magatestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.parog.magatestservice.entity.AnswerOption;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {

    Optional<AnswerOption> findByIdAndQuestionId(Long id, Long questionId);

    List<AnswerOption> findByQuestionId(Long questionId);
}

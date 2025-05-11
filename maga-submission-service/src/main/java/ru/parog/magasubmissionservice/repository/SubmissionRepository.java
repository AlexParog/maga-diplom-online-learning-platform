package ru.parog.magasubmissionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.parog.magasubmissionservice.entity.Submission;
import ru.parog.magasubmissionservice.entity.enums.SubmissionStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByUserId(Long userId);

    List<Submission> findByTestId(Long testId);

    Optional<Submission> findByIdAndUserId(Long id, Long userId);

    Optional<Submission> findByUserIdAndTestIdAndStatus(Long userId, Long testId, SubmissionStatus status);
}

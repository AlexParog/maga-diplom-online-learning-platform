package ru.parog.magasubmissionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.parog.magasubmissionservice.entity.SubmissionDetail;

@Repository
public interface SubmissionDetailRepository extends JpaRepository<SubmissionDetail, Long> {

    void deleteAllBySubmissionId(Long submissionId);
}

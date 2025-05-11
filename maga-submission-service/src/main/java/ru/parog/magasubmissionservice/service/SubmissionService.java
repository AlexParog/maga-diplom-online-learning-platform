package ru.parog.magasubmissionservice.service;

import ru.parog.magasubmissionservice.dto.SubmissionRequestDto;
import ru.parog.magasubmissionservice.dto.SubmissionResponseDto;

import java.util.List;

public interface SubmissionService {
    SubmissionResponseDto startTest(Long testId, Long userId);

    SubmissionResponseDto updateAnswers(Long submissionId, Long userId, SubmissionRequestDto request);

    SubmissionResponseDto completeTest(Long submissionId, Long userId);

    SubmissionResponseDto getSubmissionById(Long id, Long userId);

    List<SubmissionResponseDto> getUserSubmissions(Long userId);

    List<SubmissionResponseDto> getSubmissionsByTestId(Long testId);
}

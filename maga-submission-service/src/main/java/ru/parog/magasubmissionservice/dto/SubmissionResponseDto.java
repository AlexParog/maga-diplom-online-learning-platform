package ru.parog.magasubmissionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.parog.magasubmissionservice.entity.enums.SubmissionStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionResponseDto {

    private Long id;

    private SubmissionStatus status;

    private Long userId;

    private Long testId;

    private LocalDateTime submittedAt;

    private int totalScore;

    private int maxPossibleScore;

    private Integer timeSpentSeconds;

    private List<SubmissionDetailResponseDto> details;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

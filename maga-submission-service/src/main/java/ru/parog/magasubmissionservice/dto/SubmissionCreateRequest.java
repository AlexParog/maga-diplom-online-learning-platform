package ru.parog.magasubmissionservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionCreateRequest {

    @NotNull(message = "ID теста обязателен")
    private Long testId;
}

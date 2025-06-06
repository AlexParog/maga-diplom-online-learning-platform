package ru.parog.magatestservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerOptionResponseDto {

    private Long id;

    private String optionText;

    private boolean isCorrect;

    private Long questionId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

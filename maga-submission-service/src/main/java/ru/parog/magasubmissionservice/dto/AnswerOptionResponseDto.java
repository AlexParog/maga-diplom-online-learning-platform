package ru.parog.magasubmissionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerOptionResponseDto {

    private Long id;

    private String optionText;

    private boolean isCorrect;
}

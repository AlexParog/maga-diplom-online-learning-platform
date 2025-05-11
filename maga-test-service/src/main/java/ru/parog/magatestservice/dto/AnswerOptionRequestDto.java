package ru.parog.magatestservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerOptionRequestDto {

    private String optionText;

    private boolean isCorrect;

    private Long questionId;
}

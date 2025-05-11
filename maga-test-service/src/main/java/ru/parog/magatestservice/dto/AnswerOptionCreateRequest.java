package ru.parog.magatestservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerOptionCreateRequest {
    @NotBlank(message = "Текст варианта ответа не может быть пустым")
    private String optionText;

    private boolean isCorrect;

    @NotNull(message = "ID вопроса обязателен")
    private Long questionId;
}

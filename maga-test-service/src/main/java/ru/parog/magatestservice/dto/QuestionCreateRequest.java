package ru.parog.magatestservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.parog.magatestservice.entity.enums.QuestionType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionCreateRequest {
    @NotBlank(message = "Текст вопроса не может быть пустым")
    private String questionText;

    @NotNull(message = "Тип вопроса обязателен")
    private QuestionType questionType;

    @Min(value = 1, message = "Минимальное количество баллов: 1")
    private int points;

    @NotNull(message = "ID теста обязателен")
    private Long testId;
}

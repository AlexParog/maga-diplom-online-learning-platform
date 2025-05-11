package ru.parog.magatestservice.dto;

import jakarta.validation.constraints.Min;
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
public class TestCreateRequest {
    @NotBlank(message = "Название теста не может быть пустым")
    private String title;

    private String description;

    @Min(value = 1, message = "Время на прохождение теста не может быть меньше 1 минуты")
    private int timeLimit;

    @NotNull(message = "Идентификатор курса не может быть пустым")
    private Long courseId;

    @NotNull(message = "Идентификатор урока не может быть пустым")
    private Long lessonId;
}

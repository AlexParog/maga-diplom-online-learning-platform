package ru.parog.magacourseservice.dto;

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
public class LessonCreateRequest {
    @NotBlank(message = "Название урока не может быть пустым")
    private String title;

    private String content;

    private Integer orderIndex;

    @NotNull(message = "ID теста обязательно")
    private Long testId;

    @NotNull(message = "ID модуля обязательно")
    private Long moduleId;
}

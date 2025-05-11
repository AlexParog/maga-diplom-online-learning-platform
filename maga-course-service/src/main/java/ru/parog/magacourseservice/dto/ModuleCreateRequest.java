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
public class ModuleCreateRequest {
    @NotBlank(message = "Название модуля не может быть пустым")
    private String title;

    private String description;

    private Integer orderIndex;

    @NotNull(message = "Идентификатор курса не может быть пустым")
    private Long courseId;
}

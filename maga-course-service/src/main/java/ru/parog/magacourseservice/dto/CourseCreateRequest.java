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
public class CourseCreateRequest {
    @NotBlank(message = "Название курса не может быть пустым")
    private String title;

    private String description;

    @NotNull(message = "ID инструктора обязательно")
    private Long instructorId;

    private boolean published;
}

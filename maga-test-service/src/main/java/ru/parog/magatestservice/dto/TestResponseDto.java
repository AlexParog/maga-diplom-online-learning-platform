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
public class TestResponseDto {

    private Long id;

    private String title;

    private String description;

    private int timeLimit;

    private Long courseId;

    private Long lessonId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

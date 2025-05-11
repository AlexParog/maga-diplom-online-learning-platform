package ru.parog.magacourseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonResponseDto {

    private Long id;

    private String title;

    private String content;

    private int orderIndex;

    private Long moduleId;

    private Long testId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

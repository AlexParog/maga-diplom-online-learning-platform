package ru.parog.magacourseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonRequestDto {

    private String title;

    private String content;

    private Integer orderIndex;

    private Long moduleId;

    private Long testId;
}

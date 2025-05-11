package ru.parog.magatestservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestRequestDto {

    private String title;

    private String description;

    private int timeLimit;

    private Long courseId;

    private Long lessonId;
}

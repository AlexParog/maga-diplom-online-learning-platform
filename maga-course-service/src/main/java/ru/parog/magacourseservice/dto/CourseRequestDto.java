package ru.parog.magacourseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequestDto {

    private String title;

    private String description;

    private Long instructorId;

    private boolean published;
}

package ru.parog.magacourseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseCriteriaDto {
    private String title;
    private Long instructorId;
    private Boolean published;
}

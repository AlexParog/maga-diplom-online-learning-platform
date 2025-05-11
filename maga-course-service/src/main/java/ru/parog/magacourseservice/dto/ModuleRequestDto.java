package ru.parog.magacourseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuleRequestDto {

    private String title;

    private String description;

    private Integer orderIndex;

    private Long courseId;
}

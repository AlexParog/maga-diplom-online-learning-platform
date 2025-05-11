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
public class ModuleResponseDto {

    private Long id;

    private String title;

    private String description;

    private Integer orderIndex;

    private Long courseId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

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
public class EnrollmentRequestDto {

    private Long userId;

    private Long courseId;

    private String courseTitle;

    private LocalDateTime enrolledAt;

    private LocalDateTime completedAt;

    private boolean completed;

}

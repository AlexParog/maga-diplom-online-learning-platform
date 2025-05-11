package ru.parog.magauserservice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStatisticsDto {
    private Long userId;

    private int completedCourses;

    private int completedTests;

    private int totalPoints;

    private LocalDateTime lastLogin;

}

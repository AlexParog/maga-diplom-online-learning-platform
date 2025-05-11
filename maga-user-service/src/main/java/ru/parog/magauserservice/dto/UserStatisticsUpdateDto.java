package ru.parog.magauserservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStatisticsUpdateDto {
    @NotNull(message = "Завершенные тесты не могут быть null")
    @Min(value = 0, message = "Завершенные тесты должны быть неотрицательными")
    private Integer completedTests;

    @NotNull(message = "Общее количество баллов не может быть null")
    @Min(value = 0, message = "Общее количество баллов должно быть неотрицательным")
    private Integer totalPoints;
}

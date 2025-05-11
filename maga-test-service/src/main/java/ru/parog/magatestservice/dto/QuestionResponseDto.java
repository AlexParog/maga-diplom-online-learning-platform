package ru.parog.magatestservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.parog.magatestservice.entity.enums.QuestionType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponseDto {

    private Long id;

    private String questionText;

    private QuestionType questionType;

    private int points;

    private Long testId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

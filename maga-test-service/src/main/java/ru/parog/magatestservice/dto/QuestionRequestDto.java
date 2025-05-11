package ru.parog.magatestservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.parog.magatestservice.entity.enums.QuestionType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionRequestDto {

    private String questionText;

    private QuestionType questionType;

    private int points;

    private Long testId;
}

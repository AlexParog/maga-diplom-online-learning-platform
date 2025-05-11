package ru.parog.magasubmissionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerOptionSubmitRequest {

    private Long questionId;

    private List<Long> selectedOptionIds;

    private String textAnswer;
}

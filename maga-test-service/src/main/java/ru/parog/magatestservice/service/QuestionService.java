package ru.parog.magatestservice.service;

import ru.parog.magatestservice.dto.QuestionCreateRequest;
import ru.parog.magatestservice.dto.QuestionRequestDto;
import ru.parog.magatestservice.dto.QuestionResponseDto;

import java.util.List;

public interface QuestionService {
    QuestionResponseDto getQuestionById(Long id);

    QuestionResponseDto createQuestion(QuestionCreateRequest request);

    QuestionResponseDto updateQuestion(Long id, QuestionRequestDto request);

    void deleteQuestion(Long id);

    List<QuestionResponseDto> getQuestionsByTestId(Long testId);
}

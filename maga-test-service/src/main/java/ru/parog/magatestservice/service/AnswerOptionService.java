package ru.parog.magatestservice.service;

import ru.parog.magatestservice.dto.AnswerOptionCreateRequest;
import ru.parog.magatestservice.dto.AnswerOptionRequestDto;
import ru.parog.magatestservice.dto.AnswerOptionResponseDto;

import java.util.List;

public interface AnswerOptionService {
    AnswerOptionResponseDto getOptionById(Long id);

    AnswerOptionResponseDto createOption(AnswerOptionCreateRequest request);

    AnswerOptionResponseDto updateOption(Long id, AnswerOptionRequestDto request);

    void deleteOption(Long id);

    List<AnswerOptionResponseDto> getOptionsByQuestionId(Long questionId);
}

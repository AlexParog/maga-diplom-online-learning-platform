package ru.parog.magatestservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.parog.magatestservice.dto.AnswerOptionCreateRequest;
import ru.parog.magatestservice.dto.AnswerOptionRequestDto;
import ru.parog.magatestservice.dto.AnswerOptionResponseDto;
import ru.parog.magatestservice.entity.AnswerOption;
import ru.parog.magatestservice.entity.Question;
import ru.parog.magatestservice.exception.ResourceNotFoundException;
import ru.parog.magatestservice.mapper.AnswerOptionMapper;
import ru.parog.magatestservice.repository.AnswerOptionRepository;
import ru.parog.magatestservice.repository.QuestionRepository;
import ru.parog.magatestservice.service.AnswerOptionService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerOptionServiceImpl implements AnswerOptionService {

    private final AnswerOptionRepository answerOptionRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionMapper answerOptionMapper;

    @Transactional(readOnly = true)
    @Override
    public AnswerOptionResponseDto getOptionById(Long id) {
        AnswerOption option = answerOptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Вариант ответа с id %s не найден", id));

        return answerOptionMapper.toDto(option);
    }

    @Transactional
    @Override
    public AnswerOptionResponseDto createOption(AnswerOptionCreateRequest request) {
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Вопрос с id %s не найден", request.getQuestionId()));

        AnswerOption option = answerOptionMapper.fromCreateRequest(request);
        option.setQuestion(question);
        AnswerOption savedOption = answerOptionRepository.save(option);
        return answerOptionMapper.toDto(savedOption);
    }

    @Transactional
    @Override
    public AnswerOptionResponseDto updateOption(Long id, AnswerOptionRequestDto request) {
        AnswerOption option = answerOptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Вариант ответа с id %s не найден", id));

        answerOptionMapper.updateFromRequest(request, option);
        AnswerOption updatedOption = answerOptionRepository.save(option);
        return answerOptionMapper.toDto(updatedOption);
    }

    @Transactional
    @Override
    public void deleteOption(Long id) {
        if (!answerOptionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Вариант ответа с id %s не найден", id);
        }

        answerOptionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AnswerOptionResponseDto> getOptionsByQuestionId(Long questionId) {
        List<AnswerOption> options = answerOptionRepository.findByQuestionId(questionId);

        if (options.isEmpty() && !questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Вопрос с id %s не найден", questionId);
        }

        return options.stream().map(answerOptionMapper::toDto).collect(Collectors.toList());
    }
}

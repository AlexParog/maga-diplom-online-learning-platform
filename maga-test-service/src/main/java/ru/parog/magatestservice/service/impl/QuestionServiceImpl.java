package ru.parog.magatestservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.parog.magatestservice.dto.QuestionCreateRequest;
import ru.parog.magatestservice.dto.QuestionRequestDto;
import ru.parog.magatestservice.dto.QuestionResponseDto;
import ru.parog.magatestservice.entity.Question;
import ru.parog.magatestservice.entity.Test;
import ru.parog.magatestservice.exception.ResourceNotFoundException;
import ru.parog.magatestservice.mapper.QuestionMapper;
import ru.parog.magatestservice.repository.QuestionRepository;
import ru.parog.magatestservice.repository.TestRepository;
import ru.parog.magatestservice.service.QuestionService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;
    private final QuestionMapper questionMapper;

    @Transactional(readOnly = true)
    @Override
    public QuestionResponseDto getQuestionById(Long id) {
        Question question = findQuestionOrNotFound(id);

        return questionMapper.toDto(question);
    }

    @Transactional
    @Override
    public QuestionResponseDto createQuestion(QuestionCreateRequest request) {
        Test test = findTestOrNotFound(request.getTestId());

        Question question = questionMapper.fromCreateRequest(request);
        question.setTest(test);

        Question savedQuestion = questionRepository.save(question);
        return questionMapper.toDto(savedQuestion);
    }

    @Transactional
    @Override
    public QuestionResponseDto updateQuestion(Long id, QuestionRequestDto request) {
        Question question = findQuestionOrNotFound(id);

        questionMapper.updateFromRequest(request, question);
        Question updatedQuestion = questionRepository.save(question);
        return questionMapper.toDto(updatedQuestion);
    }

    @Transactional
    @Override
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Вопрос с id %s не найден", id);
        }

        questionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<QuestionResponseDto> getQuestionsByTestId(Long testId) {
        if (!testRepository.existsById(testId)) {
            throw new ResourceNotFoundException("Тест с id %s не найден", testId);
        }

        List<Question> questions = questionRepository.findByTestIdWithOptions(testId);
        return questions.stream().map(questionMapper::toDto).collect(Collectors.toList());
    }

    private Question findQuestionOrNotFound(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Вопрос с id %s не найден", id));
    }

    private Test findTestOrNotFound(Long testId) {
        return testRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Тест с id %s не найден", testId));
    }

}

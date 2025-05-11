package ru.parog.magasubmissionservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.parog.magasubmissionservice.client.TestServiceClient;
import ru.parog.magasubmissionservice.dto.AnswerOptionSubmitRequest;
import ru.parog.magasubmissionservice.dto.SubmissionRequestDto;
import ru.parog.magasubmissionservice.dto.SubmissionResponseDto;
import ru.parog.magasubmissionservice.entity.Submission;
import ru.parog.magasubmissionservice.entity.SubmissionDetail;
import ru.parog.magasubmissionservice.entity.enums.SubmissionStatus;
import ru.parog.magasubmissionservice.exception.ResourceNotFoundException;
import ru.parog.magasubmissionservice.exception.SubmissionConflictException;
import ru.parog.magasubmissionservice.mapper.SubmissionMapper;
import ru.parog.magasubmissionservice.repository.SubmissionDetailRepository;
import ru.parog.magasubmissionservice.repository.SubmissionRepository;
import ru.parog.magasubmissionservice.service.SubmissionService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final SubmissionDetailRepository submissionDetailRepository;
    private final TestServiceClient testServiceClient;
    private final SubmissionMapper submissionMapper;

    @Transactional
    @Override
    public SubmissionResponseDto startTest(Long testId, Long userId) {
        log.info("Пользователь {} начинает прохождение теста {}", userId, testId);
        try {
            // Проверка на уже существующие незавершенные прохождения
            Optional<Submission> existingSubmission = submissionRepository
                    .findByUserIdAndTestIdAndStatus(userId, testId, SubmissionStatus.IN_PROGRESS);

            if (existingSubmission.isPresent()) {
                log.info("Найдено незавершенное прохождение: {}", existingSubmission.get().getId());
                return submissionMapper.toDto(existingSubmission.get());
            }

            Submission submission = Submission.builder()
                    .status(SubmissionStatus.IN_PROGRESS)
                    .userId(userId)
                    .testId(testId)
                    .submittedAt(LocalDateTime.now())
                    .totalScore(0)
                    .maxPossibleScore(calculateMaxPossibleScore(testId))
                    .build();

            Submission savedSubmission = submissionRepository.save(submission);
            log.info("Создано новое прохождение: {}", savedSubmission.getId());
            return submissionMapper.toDto(savedSubmission);
        } catch (Exception e) {
            log.error("Ошибка при начале теста {} пользователем {}", testId, userId, e);
            throw e;
        }
    }

    @Transactional
    @Override
    public SubmissionResponseDto updateAnswers(Long submissionId, Long userId, SubmissionRequestDto request) {
        Submission submission = getSubmissionOrThrow(submissionId, userId);
        if (!submission.getStatus().equals(SubmissionStatus.IN_PROGRESS)) {
            throw new SubmissionConflictException("Прохождение теста уже завершено");
        }

        // Создаем новый список деталей вместо удаления старых
        submission.setDetails(new ArrayList<>());

        // Обрабатываем новые ответы
        List<SubmissionDetail> detailsToSave = new ArrayList<>();
        for (AnswerOptionSubmitRequest ans : request.getAnswers()) {
            SubmissionDetail detail = SubmissionDetail.builder()
                    .questionId(ans.getQuestionId())
                    .textAnswer(ans.getTextAnswer())
                    .correct(false)
                    .scoreEarned(0)
                    .submission(submission)
                    .build();

            if (ans.getSelectedOptionIds() != null && !ans.getSelectedOptionIds().isEmpty()) {
                detail.setSelectedOptionIds(ans.getSelectedOptionIds());
            }

            detailsToSave.add(detail);
        }

        // Пакетное сохранение
        submissionDetailRepository.saveAll(detailsToSave);
        submission.setDetails(detailsToSave);

        return submissionMapper.toDto(submissionRepository.save(submission));
    }

    @Transactional
    @Override
    public SubmissionResponseDto completeTest(Long submissionId, Long userId) {
        Submission submission = getSubmissionOrThrow(submissionId, userId);
        if (!submission.getStatus().equals(SubmissionStatus.IN_PROGRESS)) {
            throw new SubmissionConflictException("Прохождение теста уже завершено");
        }

        submission.setStatus(SubmissionStatus.COMPLETED);
        submission.setSubmittedAt(LocalDateTime.now());

        // Получаем все вопросы за один запрос
        List<Map<String, Object>> allQuestions = testServiceClient.getQuestionsByTestId(submission.getTestId());
        Map<Long, Map<String, Object>> questionMap = allQuestions.stream()
                .collect(Collectors.toMap(
                        q -> Long.valueOf(q.get("id").toString()),
                        q -> q
                ));

        // Предварительно загружаем все правильные варианты ответов для вопросов теста
        Map<Long, List<Map<String, Object>>> correctOptionsMap = new HashMap<>();
        for (Map<String, Object> question : allQuestions) {
            Long questionId = Long.valueOf(question.get("id").toString());
            List<Map<String, Object>> options = testServiceClient.getOptionsByQuestionId(questionId);
            List<Map<String, Object>> correctOptions = options.stream()
                    .filter(option -> Boolean.TRUE.equals(option.get("correct")))
                    .collect(Collectors.toList());
            correctOptionsMap.put(questionId, correctOptions);
        }

        // Вычисление результатов с предзагруженными данными
        int totalScore = 0;
        for (SubmissionDetail detail : submission.getDetails()) {
            Map<String, Object> question = questionMap.get(detail.getQuestionId());
            List<Map<String, Object>> correctOptions = correctOptionsMap.get(detail.getQuestionId());

            // Если вопрос не найден (возможно был удален), пропускаем
            if (question == null) {
                continue;
            }

            String questionType = question.get("questionType").toString();
            boolean isCorrect = evaluateAnswer(questionType, detail, correctOptions);
            int earned = isCorrect ? Integer.valueOf(question.get("points").toString()) : 0;

            detail.setCorrect(isCorrect);
            detail.setScoreEarned(earned);
            totalScore += earned;
        }

        submission.setTotalScore(totalScore);
        submission.setTimeSpentSeconds((int) Duration.between(submission.getCreatedAt(), LocalDateTime.now()).getSeconds());

        return submissionMapper.toDto(submissionRepository.save(submission));
    }

    @Transactional(readOnly = true)
    @Override
    public SubmissionResponseDto getSubmissionById(Long id, Long userId) {
        Submission submission = getSubmissionOrThrow(id, userId);
        return submissionMapper.toDto(submission);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubmissionResponseDto> getUserSubmissions(Long userId) {
        List<Submission> submissions = submissionRepository.findByUserId(userId);
        return submissions.stream()
                .map(submissionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubmissionResponseDto> getSubmissionsByTestId(Long testId) {
        List<Submission> submissions = submissionRepository.findByTestId(testId);
        return submissions.stream()
                .map(submissionMapper::toDto)
                .collect(Collectors.toList());
    }

    private int calculateMaxPossibleScore(Long testId) {
        List<Map<String, Object>> questions = testServiceClient.getQuestionsByTestId(testId);
        return questions.stream()
                .mapToInt(q -> Integer.parseInt(q.get("points").toString()))
                .sum();
    }

    private boolean evaluateAnswer(String questionType, SubmissionDetail detail, List<Map<String, Object>> correctOptions) {
        switch (questionType) {
            case "TEXT":
                return detail.getTextAnswer() != null && !StringUtils.hasText(detail.getTextAnswer());

            case "SINGLE_CHOICE":
                if (detail.getSelectedOptionIds() == null || detail.getSelectedOptionIds().size() != 1) {
                    return false;
                }
                // Проверяем, содержится ли выбранный ID в списке правильных ответов
                return correctOptions.stream()
                        .anyMatch(option -> Long.valueOf(option.get("id").toString()).equals(detail.getSelectedOptionIds().get(0)));

            case "MULTIPLE_CHOICE":
                if (detail.getSelectedOptionIds() == null || detail.getSelectedOptionIds().isEmpty()) {
                    return false;
                }
                // Проверяем соответствие выбранных ID и правильных ответов
                Set<Long> selectedIds = new HashSet<>(detail.getSelectedOptionIds());
                Set<Long> correctIds = correctOptions.stream()
                        .map(option -> Long.valueOf(option.get("id").toString()))
                        .collect(Collectors.toSet());
                return selectedIds.equals(correctIds);

            default:
                return false;
        }
    }

    private Submission getSubmissionOrThrow(Long id, Long userId) {
        try {
            return submissionRepository.findByIdAndUserId(id, userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Прохождение теста с id " + id + " не найдено для пользователя " + userId));
        } catch (Exception e) {
            log.error("Ошибка при получении прохождения теста: {} для пользователя {}", id, userId, e);
            throw e;
        }
    }
}

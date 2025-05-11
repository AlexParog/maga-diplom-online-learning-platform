package ru.parog.magatestservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.parog.magatestservice.client.CourseServiceClient;
import ru.parog.magatestservice.dto.TestCreateRequest;
import ru.parog.magatestservice.dto.TestRequestDto;
import ru.parog.magatestservice.dto.TestResponseDto;
import ru.parog.magatestservice.entity.Test;
import ru.parog.magatestservice.exception.ResourceNotFoundException;
import ru.parog.magatestservice.exception.TestConflictException;
import ru.parog.magatestservice.mapper.TestMapper;
import ru.parog.magatestservice.repository.TestRepository;
import ru.parog.magatestservice.service.TestService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final TestMapper testMapper;
    private final CourseServiceClient courseServiceClient;

    @Transactional(readOnly = true)
    @Override
    public List<TestResponseDto> getTestsByCourseId(Long courseId) {
        validateCourseExists(courseId);

        List<Test> tests = testRepository.findByCourseId(courseId);
        return testMapper.toDtoList(tests);
    }

    @Transactional(readOnly = true)
    @Override
    public TestResponseDto getTestByLessonId(Long lessonId) {
        Test test = testRepository.findByLessonId(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Тест для урока с id %s не найден", lessonId));

        return testMapper.toDto(test);
    }

    @Transactional(readOnly = true)
    @Override
    public TestResponseDto getTestById(Long id) {
        Test test = findTestOrNotFound(id);

        return testMapper.toDto(test);
    }

    @Transactional
    @Override
    public TestResponseDto createTest(TestCreateRequest request) {
        validateCourseExists(request.getCourseId());
        validateLessonExists(request.getLessonId());

        // Проверка, что для урока еще нет теста
        if (request.getLessonId() != null && testRepository.existsByLessonId(request.getLessonId())) {
            throw new TestConflictException("Для урока с id %s уже существует тест", request.getLessonId());
        }

        Test test = testMapper.fromCreateRequest(request);
        Test savedTest = testRepository.save(test);
        return testMapper.toDto(savedTest);
    }

    @Transactional
    @Override
    public TestResponseDto updateTest(Long id, TestRequestDto request) {
        Test test = findTestOrNotFound(id);


        // Проверка изменения связи с курсом
        if (request.getCourseId() != null && !request.getCourseId().equals(test.getCourseId())) {
            validateCourseExists(request.getCourseId());
        }

        // Проверка изменения связи с уроком
        if (request.getLessonId() != null && !request.getLessonId().equals(test.getLessonId())) {
            // Если пытаемся связать с новым уроком, проверяем его существование
            validateLessonExists(request.getLessonId());

            // Проверяем, что для нового урока еще нет теста
            if (testRepository.existsByLessonId(request.getLessonId())) {
                throw new TestConflictException("Для урока с id %s уже существует тест", request.getLessonId());
            }
        }

        testMapper.updateFromRequest(request, test);
        Test updatedTest = testRepository.save(test);

        return testMapper.toDto(updatedTest);
    }

    @Transactional
    @Override
    public void deleteTest(Long id) {
        if (!testRepository.existsById(id)) {
            throw new ResourceNotFoundException("Тест с id %s не найден", id);
        }

        // Если есть связанные вопросы, нужно их тоже удалить или отвязать
        // Здесь может быть логика очистки связанных данных или вызов других сервисов

        testRepository.deleteById(id);
    }

    private Test findTestOrNotFound(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Тест с id %s не найден", id));
    }

    private void validateCourseExists(Long courseId) {
        if (!courseServiceClient.courseExists(courseId)) {
            throw new ResourceNotFoundException("Курс с id %s не найден", courseId);
        }
    }

    private void validateLessonExists(Long lessonId) {
        if (lessonId != null && !courseServiceClient.lessonExists(lessonId)) {
            throw new ResourceNotFoundException("Урок с id %s не найден", lessonId);
        }
    }
}

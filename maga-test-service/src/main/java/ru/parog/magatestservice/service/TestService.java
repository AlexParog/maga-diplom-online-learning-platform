package ru.parog.magatestservice.service;

import ru.parog.magatestservice.dto.TestCreateRequest;
import ru.parog.magatestservice.dto.TestRequestDto;
import ru.parog.magatestservice.dto.TestResponseDto;

import java.util.List;

public interface TestService {
    List<TestResponseDto> getTestsByCourseId(Long courseId);

    TestResponseDto getTestByLessonId(Long lessonId);

    TestResponseDto getTestById(Long id);

    TestResponseDto createTest(TestCreateRequest request);

    TestResponseDto updateTest(Long id, TestRequestDto request);

    void deleteTest(Long id);
}

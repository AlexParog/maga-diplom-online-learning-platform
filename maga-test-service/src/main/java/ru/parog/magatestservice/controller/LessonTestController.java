package ru.parog.magatestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.parog.magatestservice.dto.TestResponseDto;
import ru.parog.magatestservice.service.TestService;

@RestController
@RequestMapping("/api/lessons/{lessonId}/test")
@RequiredArgsConstructor
public class LessonTestController {

    private final TestService testService;

    @GetMapping
    public TestResponseDto getTestByLessonId(@PathVariable Long lessonId) {
        return testService.getTestByLessonId(lessonId);
    }
}

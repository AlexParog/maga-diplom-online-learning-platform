package ru.parog.magatestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.parog.magatestservice.dto.TestResponseDto;
import ru.parog.magatestservice.service.TestService;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/tests")
@RequiredArgsConstructor
public class CourseTestController {

    private final TestService testService;

    @GetMapping
    public List<TestResponseDto> getTestsByCourseId(@PathVariable Long courseId) {
        return testService.getTestsByCourseId(courseId);
    }
}

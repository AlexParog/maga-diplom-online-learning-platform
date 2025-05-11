package ru.parog.magacourseservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.parog.magacourseservice.dto.EnrollmentResponseDto;
import ru.parog.magacourseservice.service.EnrollmentService;

@RestController
@RequestMapping("/api/courses/{courseId}/enroll")
@RequiredArgsConstructor
public class CourseEnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public EnrollmentResponseDto enrollUser(
            @PathVariable Long courseId,
            @RequestParam Long userId) {

        return enrollmentService.enrollUserToCourse(courseId, userId);
    }
}

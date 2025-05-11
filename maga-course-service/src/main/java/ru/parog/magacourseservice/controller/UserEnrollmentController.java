package ru.parog.magacourseservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.parog.magacourseservice.dto.EnrollmentResponseDto;
import ru.parog.magacourseservice.service.EnrollmentService;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/enrollments")
@RequiredArgsConstructor
public class UserEnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping
    public List<EnrollmentResponseDto> getEnrollmentsByUserId(@PathVariable Long userId) {
        return enrollmentService.getEnrollmentsByUserId(userId);
    }
}

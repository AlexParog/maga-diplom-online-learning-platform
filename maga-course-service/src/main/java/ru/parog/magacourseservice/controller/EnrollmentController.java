package ru.parog.magacourseservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.parog.magacourseservice.dto.EnrollmentResponseDto;
import ru.parog.magacourseservice.service.EnrollmentService;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PutMapping("/{id}/complete")
    public EnrollmentResponseDto completeEnrollment(@PathVariable Long id) {
        return enrollmentService.completeEnrollment(id);
    }
}

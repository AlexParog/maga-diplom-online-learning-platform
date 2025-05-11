package ru.parog.magacourseservice.service;

import ru.parog.magacourseservice.dto.EnrollmentResponseDto;

import java.util.List;

public interface EnrollmentService {

    EnrollmentResponseDto enrollUserToCourse(Long courseId, Long userId);

    List<EnrollmentResponseDto> getEnrollmentsByUserId(Long userId);

    EnrollmentResponseDto completeEnrollment(Long id);
}

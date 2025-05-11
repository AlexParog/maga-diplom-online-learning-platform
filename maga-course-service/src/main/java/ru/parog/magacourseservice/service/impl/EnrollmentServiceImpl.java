package ru.parog.magacourseservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.parog.magacourseservice.dto.EnrollmentResponseDto;
import ru.parog.magacourseservice.entity.Course;
import ru.parog.magacourseservice.entity.Enrollment;
import ru.parog.magacourseservice.exception.CourseNotAvailableException;
import ru.parog.magacourseservice.exception.EnrollmentConflictException;
import ru.parog.magacourseservice.exception.ResourceNotFoundException;
import ru.parog.magacourseservice.mapper.EnrollmentMapper;
import ru.parog.magacourseservice.repository.CourseRepository;
import ru.parog.magacourseservice.repository.EnrollmentRepository;
import ru.parog.magacourseservice.service.EnrollmentService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Transactional
    @Override
    public EnrollmentResponseDto enrollUserToCourse(Long courseId, Long userId) {
        if (enrollmentRepository.existsByUserIdAndCourseId(userId, courseId)) {
            throw new EnrollmentConflictException("Пользователь уже зачислен на курс");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Курс с id %s не найден", courseId));

        // Проверка, доступен ли курс для зачисления
        if (!course.isPublished()) {
            throw new CourseNotAvailableException("Курс недоступен для зачисления");
        }

        Enrollment enrollment = Enrollment.builder()
                .userId(userId)
                .course(course)
                .enrolledAt(LocalDateTime.now())
                .completed(false)
                .build();

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDto(savedEnrollment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EnrollmentResponseDto> getEnrollmentsByUserId(Long userId) {
        List<Enrollment> enrollments = enrollmentRepository.findByUserId(userId);
        return enrollmentMapper.toDtoList(enrollments);
    }

    @Transactional
    @Override
    public EnrollmentResponseDto completeEnrollment(Long id) {
        Enrollment enrollment = findEnrollmentOrNotFound(id);

        enrollment.setCompleted(true);
        enrollment.setCompletedAt(LocalDateTime.now());
        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);

        return enrollmentMapper.toDto(updatedEnrollment);
    }

    private Enrollment findEnrollmentOrNotFound(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Зачисление с id %s не найдено", id));
    }

}

package ru.parog.magacourseservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.parog.magacourseservice.dto.*;
import ru.parog.magacourseservice.entity.Course;
import ru.parog.magacourseservice.exception.ResourceNotFoundException;
import ru.parog.magacourseservice.mapper.CourseMapper;
import ru.parog.magacourseservice.repository.CourseRepository;
import ru.parog.magacourseservice.service.CourseService;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Transactional(readOnly = true)
    @Override
    public PageResponse<CourseResponseDto> list(CourseCriteriaDto criteria, Pageable pageable) {
        log.debug("Получение списка курсов с критериями: {}, страница: {}", criteria, pageable);
        Page<Course> coursePage = courseRepository.findWithFilters(
                criteria.getTitle(),
                criteria.getInstructorId(),
                criteria.getPublished(),
                pageable
        );

        return new PageResponse<>(
                courseMapper.toDtoList(coursePage.getContent()),
                pageable.getPageNumber(),
                pageable.getPageSize(),
                coursePage.getTotalElements(),
                coursePage.getTotalPages(),
                coursePage.isLast()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public CourseResponseDto getById(Long id) {
        log.debug("Получение курса по ID: {}", id);
        Course course = findCourseOrNotFound(id);

        return courseMapper.toDto(course);
    }

    @Transactional
    @Override
    public CourseResponseDto createCourse(CourseCreateRequest courseCreateRequest) {
        log.debug("Создание нового курса: {}", courseCreateRequest);
        Course course = courseMapper.fromCreateRequest(courseCreateRequest);
        Course savedCourse = courseRepository.save(course);

        return courseMapper.toDto(savedCourse);
    }


    @Transactional
    @Override
    public CourseResponseDto updateCourse(Long id, CourseRequestDto courseRequestDto) {
        log.debug("Обновление курса с ID: {}, данные: {}", id, courseRequestDto);
        Course course = findCourseOrNotFound(id);

        courseMapper.updateFromDto(courseRequestDto, course);
        Course updatedCourse = courseRepository.save(course);

        return courseMapper.toDto(updatedCourse);
    }

    @Transactional
    @Override
    public void deleteCourse(Long id) {
        log.debug("Удаление курса с ID: {}", id);
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Курс с id %s не найден", id);
        }
        courseRepository.deleteById(id);
    }

    private Course findCourseOrNotFound(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Курс с id %s не найден", id));
    }

}

package ru.parog.magacourseservice.service;

import org.springframework.data.domain.Pageable;
import ru.parog.magacourseservice.dto.*;

public interface CourseService {

    PageResponse<CourseResponseDto> list(CourseCriteriaDto criteria, Pageable pageable);

    CourseResponseDto getById(Long id);

    CourseResponseDto createCourse(CourseCreateRequest courseRequestDto);

    CourseResponseDto updateCourse(Long id, CourseRequestDto courseRequestDto);

    void deleteCourse(Long id);
}

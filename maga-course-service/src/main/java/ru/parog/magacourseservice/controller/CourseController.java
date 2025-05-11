package ru.parog.magacourseservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.parog.magacourseservice.dto.*;
import ru.parog.magacourseservice.service.CourseService;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public PageResponse<CourseResponseDto> list(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long instructorId,
            @RequestParam(required = false) Boolean published,
            Pageable pageable) {

        CourseCriteriaDto criteria = new CourseCriteriaDto();
        criteria.setTitle(title);
        criteria.setInstructorId(instructorId);
        criteria.setPublished(published);

        PageResponse<CourseResponseDto> response = courseService.list(criteria, pageable);
        return response;
    }

    @GetMapping("/{id}")
    public CourseResponseDto getById(@PathVariable Long id) {
        return courseService.getById(id);
    }

    @PostMapping
    public CourseResponseDto create(@RequestBody @Valid CourseCreateRequest request) {
        return courseService.createCourse(request);
    }

    @PutMapping("/{id}")
    public CourseResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid CourseRequestDto request) {

        return courseService.updateCourse(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }
}

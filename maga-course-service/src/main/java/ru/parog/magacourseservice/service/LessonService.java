package ru.parog.magacourseservice.service;


import ru.parog.magacourseservice.dto.LessonCreateRequest;
import ru.parog.magacourseservice.dto.LessonRequestDto;
import ru.parog.magacourseservice.dto.LessonResponseDto;

import java.util.List;

public interface LessonService {

    LessonResponseDto getLessonById(Long id);

    List<LessonResponseDto> getLessonsByModuleId(Long moduleId);

    LessonResponseDto createLesson(LessonCreateRequest lessonCreateRequest);

    LessonResponseDto updateLesson(Long id, LessonRequestDto request);

    void deleteLesson(Long id);
}

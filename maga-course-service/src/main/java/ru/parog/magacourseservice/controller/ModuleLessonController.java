package ru.parog.magacourseservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.parog.magacourseservice.dto.LessonCreateRequest;
import ru.parog.magacourseservice.dto.LessonResponseDto;
import ru.parog.magacourseservice.service.LessonService;

import java.util.List;

@RestController
@RequestMapping("/api/modules/{moduleId}/lessons")
@RequiredArgsConstructor
public class ModuleLessonController {

    private final LessonService lessonService;

    @GetMapping
    public List<LessonResponseDto> getLessonsByModuleId(@PathVariable Long moduleId) {
        return lessonService.getLessonsByModuleId(moduleId);
    }

    @PostMapping
    public LessonResponseDto createLesson(@RequestBody @Valid LessonCreateRequest request) {
        return lessonService.createLesson(request);
    }
}

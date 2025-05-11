package ru.parog.magacourseservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.parog.magacourseservice.dto.LessonRequestDto;
import ru.parog.magacourseservice.dto.LessonResponseDto;
import ru.parog.magacourseservice.service.LessonService;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @GetMapping("/{id}")
    public LessonResponseDto getById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @PutMapping("/{id}")
    public LessonResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid LessonRequestDto request) {

        return lessonService.updateLesson(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        lessonService.deleteLesson(id);
    }

}

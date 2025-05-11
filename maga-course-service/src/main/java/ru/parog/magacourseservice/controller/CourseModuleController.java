package ru.parog.magacourseservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.parog.magacourseservice.dto.ModuleCreateRequest;
import ru.parog.magacourseservice.dto.ModuleResponseDto;
import ru.parog.magacourseservice.service.ModuleService;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/modules")
@RequiredArgsConstructor
public class CourseModuleController {

    private final ModuleService moduleService;

    @GetMapping
    public List<ModuleResponseDto> getModulesByCourseId(@PathVariable Long courseId) {
        return moduleService.getModulesByCourseId(courseId);
    }

    @PostMapping
    public ModuleResponseDto createModule(@RequestBody @Valid ModuleCreateRequest request) {

        return moduleService.createModule(request);
    }
}

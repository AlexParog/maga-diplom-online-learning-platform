package ru.parog.magacourseservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.parog.magacourseservice.dto.ModuleRequestDto;
import ru.parog.magacourseservice.dto.ModuleResponseDto;
import ru.parog.magacourseservice.service.ModuleService;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    @GetMapping("/{id}")
    public ModuleResponseDto getById(@PathVariable Long id) {
        return moduleService.getModuleById(id);
    }

    @PutMapping("/{id}")
    public ModuleResponseDto update(
            @PathVariable Long id,
            @RequestBody @Valid ModuleRequestDto request) {

        return moduleService.updateModule(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        moduleService.deleteModule(id);
    }
}

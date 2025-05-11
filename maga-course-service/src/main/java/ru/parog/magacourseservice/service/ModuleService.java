package ru.parog.magacourseservice.service;

import ru.parog.magacourseservice.dto.ModuleCreateRequest;
import ru.parog.magacourseservice.dto.ModuleRequestDto;
import ru.parog.magacourseservice.dto.ModuleResponseDto;

import java.util.List;

public interface ModuleService {

    List<ModuleResponseDto> getModulesByCourseId(Long courseId);

    ModuleResponseDto getModuleById(Long id);

    ModuleResponseDto createModule(ModuleCreateRequest moduleRequestDto);

    ModuleResponseDto updateModule(Long id, ModuleRequestDto moduleRequestDto);

    void deleteModule(Long id);
}

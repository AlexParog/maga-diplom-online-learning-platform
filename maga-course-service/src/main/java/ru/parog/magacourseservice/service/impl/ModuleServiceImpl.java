package ru.parog.magacourseservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.parog.magacourseservice.dto.ModuleCreateRequest;
import ru.parog.magacourseservice.dto.ModuleRequestDto;
import ru.parog.magacourseservice.dto.ModuleResponseDto;
import ru.parog.magacourseservice.entity.Course;
import ru.parog.magacourseservice.exception.ResourceNotFoundException;
import ru.parog.magacourseservice.mapper.ModuleMapper;
import ru.parog.magacourseservice.repository.CourseRepository;
import ru.parog.magacourseservice.repository.ModuleRepository;
import ru.parog.magacourseservice.service.ModuleService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;
    private final ModuleMapper moduleMapper;

    @Transactional(readOnly = true)
    @Override
    public List<ModuleResponseDto> getModulesByCourseId(Long courseId) {
        log.debug("Получение модулей для курса с ID: {}", courseId);

        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Курс с id %s не найден", courseId);
        }

        List<ru.parog.magacourseservice.entity.Module> modules = moduleRepository.findByCourseIdOrderByOrderIndex(courseId);
        return moduleMapper.toDtoList(modules);
    }

    @Transactional(readOnly = true)
    @Override
    public ModuleResponseDto getModuleById(Long id) {
        log.debug("Получение модуля по ID: {}", id);
        ru.parog.magacourseservice.entity.Module module = findModuleOrNotFound(id);

        return moduleMapper.toDto(module);
    }

    @Transactional
    @Override
    public ModuleResponseDto createModule(ModuleCreateRequest moduleCreateRequest) {
        log.debug("Создание нового модуля: {}", moduleCreateRequest);

        Course course = courseRepository.findById(moduleCreateRequest.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Курс с id %s не найден", moduleCreateRequest.getCourseId()));

        ru.parog.magacourseservice.entity.Module module = moduleMapper.fromCreateRequest(moduleCreateRequest);
        module.setCourse(course);

        if (module.getOrderIndex() == null) {
            Integer maxOrderIndex = moduleRepository.findMaxOrderIndexByCourseId(moduleCreateRequest.getCourseId()).orElse(-1);
            module.setOrderIndex(maxOrderIndex + 1);
        }

        ru.parog.magacourseservice.entity.Module savedModule = moduleRepository.save(module);
        return moduleMapper.toDto(savedModule);
    }

    @Transactional
    @Override
    public ModuleResponseDto updateModule(Long id, ModuleRequestDto moduleRequestDto) {
        log.debug("Обновление модуля с ID: {}, данные: {}", id, moduleRequestDto);

        ru.parog.magacourseservice.entity.Module module = findModuleOrNotFound(id);

        moduleMapper.updateFromRequest(moduleRequestDto, module);
        ru.parog.magacourseservice.entity.Module updatedModule = moduleRepository.save(module);

        return moduleMapper.toDto(updatedModule);
    }

    @Transactional
    @Override
    public void deleteModule(Long id) {
        log.debug("Удаление модуля с ID: {}", id);

        if (!moduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Модуль с id %s не найден", id);
        }

        moduleRepository.deleteById(id);
    }

    private ru.parog.magacourseservice.entity.Module findModuleOrNotFound(Long id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Модуль с id %s не найден", id));
    }
}

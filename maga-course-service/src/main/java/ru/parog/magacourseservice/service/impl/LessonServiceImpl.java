package ru.parog.magacourseservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.parog.magacourseservice.dto.LessonCreateRequest;
import ru.parog.magacourseservice.dto.LessonRequestDto;
import ru.parog.magacourseservice.dto.LessonResponseDto;
import ru.parog.magacourseservice.entity.Lesson;
import ru.parog.magacourseservice.exception.ResourceNotFoundException;
import ru.parog.magacourseservice.exception.ValidationException;
import ru.parog.magacourseservice.mapper.LessonMapper;
import ru.parog.magacourseservice.repository.LessonRepository;
import ru.parog.magacourseservice.repository.ModuleRepository;
import ru.parog.magacourseservice.service.LessonService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;
    private final LessonMapper lessonMapper;

    @Transactional(readOnly = true)
    @Override
    public List<LessonResponseDto> getLessonsByModuleId(Long moduleId) {
        if (!moduleRepository.existsById(moduleId)) {
            throw new ResourceNotFoundException("Модуль с id %s не найден", moduleId);
        }

        List<Lesson> lessons = lessonRepository.findByModuleIdOrderByOrderIndex(moduleId);
        return lessonMapper.toDtoList(lessons);
    }

    @Transactional(readOnly = true)
    @Override
    public LessonResponseDto getLessonById(Long id) {
        Lesson lesson = findLessonOrNotFound(id);
        return lessonMapper.toDto(lesson);
    }

    @Transactional
    @Override
    public LessonResponseDto createLesson(LessonCreateRequest lessonCreateRequest) {
        ru.parog.magacourseservice.entity.Module module = moduleRepository.findById(lessonCreateRequest.getModuleId())
                .orElseThrow(() -> new ResourceNotFoundException("Модуль с id %s не найден", lessonCreateRequest.getModuleId()));

        Lesson lesson = lessonMapper.fromCreateRequest(lessonCreateRequest);
        lesson.setModule(module);

        if (lesson.getOrderIndex() == null) {
            // Используем синхронизированный блок для безопасного доступа к максимальному индексу
            synchronized (this) {
                Integer maxOrderIndex = lessonRepository.findMaxOrderIndexByModuleId(lessonCreateRequest.getModuleId()).orElse(-1);
                lesson.setOrderIndex(maxOrderIndex + 1);
            }
        }

        Lesson savedLesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(savedLesson);
    }

    @Transactional
    @Override
    public LessonResponseDto updateLesson(Long id, LessonRequestDto request) {
        Lesson lesson = findLessonOrNotFound(id);

        // Если изменяется orderIndex, нужно проверить его валидность
        if (request.getOrderIndex() != null && !request.getOrderIndex().equals(lesson.getOrderIndex())) {
            Integer maxOrderIndex = lessonRepository.findMaxOrderIndexByModuleId(lesson.getModule().getId()).orElse(0);
            if (request.getOrderIndex() > maxOrderIndex) {
                throw new ValidationException("Недопустимый порядковый индекс: %d (максимальный: %d)",
                        request.getOrderIndex(), maxOrderIndex);
            }
        }

        lessonMapper.updateFromRequest(request, lesson);
        Lesson updatedLesson = lessonRepository.save(lesson);

        return lessonMapper.toDto(updatedLesson);
    }

    @Transactional
    @Override
    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Урок с id %s не найден", id);
        }
        Lesson lesson = findLessonOrNotFound(id);
        Long moduleId = lesson.getModule().getId();
        Integer orderIndex = lesson.getOrderIndex();

        lessonRepository.deleteById(id);

        // Обновление orderIndex для оставшихся уроков
        lessonRepository.decrementOrderIndexForLessonsAfter(moduleId, orderIndex);
    }

    private Lesson findLessonOrNotFound(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Урок с id %s не найден", id));
    }
}

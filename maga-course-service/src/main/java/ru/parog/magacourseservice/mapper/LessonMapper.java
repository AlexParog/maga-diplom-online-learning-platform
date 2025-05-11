package ru.parog.magacourseservice.mapper;

import org.mapstruct.*;
import ru.parog.magacourseservice.dto.LessonCreateRequest;
import ru.parog.magacourseservice.dto.LessonRequestDto;
import ru.parog.magacourseservice.dto.LessonResponseDto;
import ru.parog.magacourseservice.entity.Lesson;

import java.util.List;


@Mapper(componentModel = "spring")
public interface LessonMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "orderIndex", source = "orderIndex"),
            @Mapping(target = "moduleId", source = "module.id"),
            @Mapping(target = "testId", source = "testId"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt")
    })
    LessonResponseDto toDto(Lesson lesson);

    @InheritConfiguration(name = "toDto")
    List<LessonResponseDto> toDtoList(List<Lesson> lessons);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "testId", source = "testId")
    })
    Lesson fromCreateRequest(LessonCreateRequest request);

    void updateFromRequest(LessonRequestDto dto, @MappingTarget Lesson lesson);
}

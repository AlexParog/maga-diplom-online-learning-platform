package ru.parog.magacourseservice.mapper;

import org.mapstruct.*;
import ru.parog.magacourseservice.dto.ModuleCreateRequest;
import ru.parog.magacourseservice.dto.ModuleRequestDto;
import ru.parog.magacourseservice.dto.ModuleResponseDto;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface ModuleMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "orderIndex", source = "orderIndex"),
            @Mapping(target = "courseId", source = "course.id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt")
    })
    ModuleResponseDto toDto(ru.parog.magacourseservice.entity.Module module);

    @InheritConfiguration(name = "toDto")
    List<ModuleResponseDto> toDtoList(List<ru.parog.magacourseservice.entity.Module> modules);

    @Mapping(target = "id", ignore = true)
    ru.parog.magacourseservice.entity.Module fromCreateRequest(ModuleCreateRequest request);

    void updateFromRequest(ModuleRequestDto dto, @MappingTarget ru.parog.magacourseservice.entity.Module module);
}

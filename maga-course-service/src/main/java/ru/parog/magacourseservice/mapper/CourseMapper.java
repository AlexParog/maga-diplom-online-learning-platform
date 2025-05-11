package ru.parog.magacourseservice.mapper;

import org.mapstruct.*;
import ru.parog.magacourseservice.dto.CourseCreateRequest;
import ru.parog.magacourseservice.dto.CourseRequestDto;
import ru.parog.magacourseservice.dto.CourseResponseDto;
import ru.parog.magacourseservice.entity.Course;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "instructorId", source = "instructorId"),
            @Mapping(target = "published", source = "published"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt")
    })
    CourseResponseDto toDto(Course course);

    @InheritConfiguration(name = "toDto")
    List<CourseResponseDto> toDtoList(List<Course> courses);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "instructorId", source = "instructorId"),
            @Mapping(target = "published", source = "published")
    })
    Course fromCreateRequest(CourseCreateRequest request);

    void updateFromDto(CourseRequestDto dto, @MappingTarget Course course);
}

package ru.parog.magacourseservice.mapper;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.parog.magacourseservice.dto.EnrollmentRequestDto;
import ru.parog.magacourseservice.dto.EnrollmentResponseDto;
import ru.parog.magacourseservice.entity.Enrollment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "courseId", source = "course.id"),
            @Mapping(target = "courseTitle", source = "course.title"),
            @Mapping(target = "enrolledAt", source = "enrolledAt"),
            @Mapping(target = "completedAt", source = "completedAt"),
            @Mapping(target = "completed", source = "completed"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt")
    })
    EnrollmentResponseDto toDto(Enrollment enrollment);

    @InheritConfiguration(name = "toDto")
    List<EnrollmentResponseDto> toDtoList(List<Enrollment> enrollments);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "enrolledAt", source = "enrolledAt"),
            @Mapping(target = "completedAt", source = "completedAt"),
            @Mapping(target = "completed", source = "completed")
    })
    Enrollment fromCreateRequest(EnrollmentRequestDto dto);
}

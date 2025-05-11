package ru.parog.magatestservice.mapper;

import org.mapstruct.*;
import ru.parog.magatestservice.dto.TestCreateRequest;
import ru.parog.magatestservice.dto.TestRequestDto;
import ru.parog.magatestservice.dto.TestResponseDto;
import ru.parog.magatestservice.entity.Test;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TestMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "timeLimit", source = "timeLimit"),
            @Mapping(target = "courseId", source = "courseId"),
            @Mapping(target = "lessonId", source = "lessonId"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt")
    })
    TestResponseDto toDto(Test test);

    @InheritConfiguration(name = "toDto")
    List<TestResponseDto> toDtoList(List<Test> tests);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "courseId", source = "courseId"),
            @Mapping(target = "lessonId", source = "lessonId")
    })
    Test fromCreateRequest(TestCreateRequest request);

    void updateFromRequest(TestRequestDto dto, @MappingTarget Test test);
}

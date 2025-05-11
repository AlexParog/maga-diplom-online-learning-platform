package ru.parog.magatestservice.mapper;

import org.mapstruct.*;
import ru.parog.magatestservice.dto.QuestionCreateRequest;
import ru.parog.magatestservice.dto.QuestionRequestDto;
import ru.parog.magatestservice.dto.QuestionResponseDto;
import ru.parog.magatestservice.entity.Question;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "questionText", source = "questionText"),
            @Mapping(target = "questionType", source = "questionType"),
            @Mapping(target = "points", source = "points"),
            @Mapping(target = "testId", source = "test.id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt")
    })
    QuestionResponseDto toDto(Question question);

    @InheritConfiguration(name = "toDto")
    List<QuestionResponseDto> toDtoList(List<Question> questions);

    @Mapping(target = "id", ignore = true)
    Question fromCreateRequest(QuestionCreateRequest request);

    void updateFromRequest(QuestionRequestDto dto, @MappingTarget Question question);
}

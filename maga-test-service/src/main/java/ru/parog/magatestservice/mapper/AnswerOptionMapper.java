package ru.parog.magatestservice.mapper;

import org.mapstruct.*;
import ru.parog.magatestservice.dto.AnswerOptionCreateRequest;
import ru.parog.magatestservice.dto.AnswerOptionRequestDto;
import ru.parog.magatestservice.dto.AnswerOptionResponseDto;
import ru.parog.magatestservice.entity.AnswerOption;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerOptionMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "optionText", source = "optionText"),
            @Mapping(target = "isCorrect", source = "isCorrect"),
            @Mapping(target = "questionId", source = "question.id"),
            @Mapping(target = "createdAt", source = "createdAt"),
            @Mapping(target = "updatedAt", source = "updatedAt")
    })
    AnswerOptionResponseDto toDto(AnswerOption answerOption);

    @InheritConfiguration(name = "toDto")
    List<AnswerOptionResponseDto> toDtoList(List<AnswerOption> options);


    @Mapping(target = "id", ignore = true)
    AnswerOption fromCreateRequest(AnswerOptionCreateRequest request);

    void updateFromRequest(AnswerOptionRequestDto dto, @MappingTarget AnswerOption option);
}

package ru.parog.magasubmissionservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.parog.magasubmissionservice.dto.SubmissionDetailResponseDto;
import ru.parog.magasubmissionservice.entity.SubmissionDetail;

@Mapper(componentModel = "spring")
public interface SubmissionDetailMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "questionId", source = "questionId"),
            @Mapping(target = "selectedOptionIds", source = "selectedOptionIds"),
            @Mapping(target = "textAnswer", source = "textAnswer"),
            @Mapping(target = "correct", source = "correct"),
            @Mapping(target = "scoreEarned", source = "scoreEarned")
    })
    SubmissionDetailResponseDto toDto(SubmissionDetail detail);
}

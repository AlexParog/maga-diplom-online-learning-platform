package ru.parog.magasubmissionservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.parog.magasubmissionservice.dto.SubmissionResponseDto;
import ru.parog.magasubmissionservice.entity.Submission;

@Mapper(componentModel = "spring", uses = {SubmissionDetailMapper.class})
public interface SubmissionMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "status", source = "status"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "testId", source = "testId"),
            @Mapping(target = "submittedAt", source = "submittedAt"),
            @Mapping(target = "totalScore", source = "totalScore"),
            @Mapping(target = "maxPossibleScore", source = "maxPossibleScore"),
            @Mapping(target = "timeSpentSeconds", source = "timeSpentSeconds"),
            @Mapping(target = "details", source = "details")
    })
    SubmissionResponseDto toDto(Submission submission);
}

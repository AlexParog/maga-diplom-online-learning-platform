package ru.parog.magauserservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.parog.magauserservice.dto.UserStatisticsDto;
import ru.parog.magauserservice.dto.UserStatisticsUpdateDto;
import ru.parog.magauserservice.entity.UserStatistics;


@Mapper(componentModel = "spring")
public interface UserStatisticsMapper {

    @Mapping(source = "user.id", target = "userId")
    UserStatisticsDto toDto(UserStatistics userStatistics);

    default void updateFromDto(UserStatisticsUpdateDto dto, @MappingTarget UserStatistics entity) {
        // Устанавливаем значения напрямую из DTO, если они не null
        // Проверка на null важна, если submission-service может прислать DTO,
        if (dto.getCompletedTests() != null) {
            entity.setCompletedTests(dto.getCompletedTests());
        }
        if (dto.getTotalPoints() != null) {
            entity.setTotalPoints(dto.getTotalPoints());
        }
    }
}

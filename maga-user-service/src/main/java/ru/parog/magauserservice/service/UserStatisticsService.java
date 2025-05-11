package ru.parog.magauserservice.service;


import ru.parog.magauserservice.dto.UserStatisticsDto;
import ru.parog.magauserservice.dto.UserStatisticsUpdateDto;

public interface UserStatisticsService {
    UserStatisticsDto getUserStatistics(Long userId);

    UserStatisticsDto updateUserStatistics(Long userId, UserStatisticsUpdateDto userStatisticsUpdateDto);
}

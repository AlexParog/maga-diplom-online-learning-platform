package ru.parog.magauserservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.parog.magauserservice.dto.UserStatisticsDto;
import ru.parog.magauserservice.dto.UserStatisticsUpdateDto;
import ru.parog.magauserservice.entity.User;
import ru.parog.magauserservice.entity.UserStatistics;
import ru.parog.magauserservice.exception.UserNotFoundException;
import ru.parog.magauserservice.exception.UserStatisticsNotFoundException;
import ru.parog.magauserservice.mapper.UserStatisticsMapper;
import ru.parog.magauserservice.repository.UserRepository;
import ru.parog.magauserservice.repository.UserStatisticsRepository;
import ru.parog.magauserservice.service.UserStatisticsService;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserStatisticsServiceImpl implements UserStatisticsService {

    private final UserStatisticsRepository userStatisticsRepository;
    private final UserRepository userRepository;
    private final UserStatisticsMapper userStatisticsMapper;

    @Transactional(readOnly = true)
    public UserStatisticsDto getUserStatistics(Long userId) {
        UserStatistics statistics = findUserStatisticsOrThrow(userId);
        return userStatisticsMapper.toDto(statistics);
    }

    @Transactional
    public UserStatisticsDto updateUserStatistics(Long userId, UserStatisticsUpdateDto updateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден с ID: " + userId));

        UserStatistics statistics = userStatisticsRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserStatistics newStats = new UserStatistics();
                    newStats.setUser(user);
                    return newStats;
                });

        userStatisticsMapper.updateFromDto(updateDto, statistics);
        statistics = userStatisticsRepository.save(statistics);

        return userStatisticsMapper.toDto(statistics);
    }

    private UserStatistics findUserStatisticsOrThrow(Long userId) {
        return userStatisticsRepository.findById(userId)
                .orElseThrow(() -> new UserStatisticsNotFoundException("Пользовательские статистики не найдены для пользователя id: " + userId));
    }
}

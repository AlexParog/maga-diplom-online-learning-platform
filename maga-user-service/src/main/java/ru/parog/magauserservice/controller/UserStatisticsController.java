package ru.parog.magauserservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.parog.magauserservice.dto.UserStatisticsDto;
import ru.parog.magauserservice.dto.UserStatisticsUpdateDto;
import ru.parog.magauserservice.service.UserStatisticsService;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserStatisticsController {

    private final UserStatisticsService userStatisticsService;

    @GetMapping("/{userId}/statistics")
    // @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public UserStatisticsDto getUserStatistics(@PathVariable Long userId) {
        return userStatisticsService.getUserStatistics(userId);
    }

    @PutMapping("/{userId}/statistics")
    // @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_INSTRUCTOR')")
    public UserStatisticsDto updateUserStatistics(@PathVariable Long userId, @RequestBody UserStatisticsUpdateDto userStatisticsUpdateDto) {
        return userStatisticsService.updateUserStatistics(userId, userStatisticsUpdateDto);
    }
}

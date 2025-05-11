package ru.parog.magauserservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.parog.magauserservice.entity.UserStatistics;

import java.util.Optional;


public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Long> {
    Optional<UserStatistics> findByUserId(Long userId);
}

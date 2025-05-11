package ru.parog.magauserservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = UserStatistics.TABLE_NAME)
@Getter
@Setter
public class UserStatistics {
    public static final String TABLE_NAME = "user_statistics";

    static class ColumnNames {
        public static final String USER_STATISTICS_CN_COMPLETED_COURSES = "completed_courses";
        public static final String USER_STATISTICS_CN_COMPLETED_TESTS = "completed_tests";
        public static final String USER_STATISTICS_CN_TOTAL_POINTS = "total_points";
        public static final String USER_STATISTICS_CN_LAST_LOGIN = "last_login";
    }

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = ColumnNames.USER_STATISTICS_CN_COMPLETED_COURSES)
    private int completedCourses;

    @Column(name = ColumnNames.USER_STATISTICS_CN_COMPLETED_TESTS)
    private int completedTests;

    @Column(name = ColumnNames.USER_STATISTICS_CN_TOTAL_POINTS)
    private int totalPoints;

    @Column(name = ColumnNames.USER_STATISTICS_CN_LAST_LOGIN)
    private LocalDateTime lastLogin;

}

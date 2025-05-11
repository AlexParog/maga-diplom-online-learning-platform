package ru.parog.magasubmissionservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.parog.magasubmissionservice.entity.enums.SubmissionStatus;
import ru.parog.onlinelearningplatformmodel.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = Submission.TABLE_NAME)
public class Submission extends BaseEntity {

    public static final String TABLE_NAME = "submissions";

    static class ColumnNames {
        public static final String SUBMISSION_CN_STATUS = "status";
        public static final String SUBMISSION_CN_USER_ID = "user_id";
        public static final String SUBMISSION_CN_TEST_ID = "test_id";
        public static final String SUBMISSION_CN_SUBMITTED_AT = "submitted_at";
        public static final String SUBMISSION_CN_TOTAL_SCORE = "total_score";
        public static final String SUBMISSION_CN_MAX_POSSIBLE_SCORE = "max_possible_score";
        public static final String SUBMISSION_CN_TIME_SPENT_SECONDS = "time_spent_seconds";
    }

    @Enumerated(EnumType.STRING)
    @Column(name = ColumnNames.SUBMISSION_CN_STATUS, nullable = false)
    private SubmissionStatus status;

    @Column(name = ColumnNames.SUBMISSION_CN_USER_ID, nullable = false)
    private Long userId;

    @Column(name = ColumnNames.SUBMISSION_CN_TEST_ID, nullable = false)
    private Long testId;

    @Column(name = ColumnNames.SUBMISSION_CN_SUBMITTED_AT, nullable = false)
    private LocalDateTime submittedAt;

    @Column(name = ColumnNames.SUBMISSION_CN_TOTAL_SCORE, nullable = false)
    private int totalScore;

    @Column(name = ColumnNames.SUBMISSION_CN_MAX_POSSIBLE_SCORE, nullable = false)
    private int maxPossibleScore;

    @Column(name = ColumnNames.SUBMISSION_CN_TIME_SPENT_SECONDS)
    private Integer timeSpentSeconds;

    @OneToMany(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubmissionDetail> details = new ArrayList<>();
}

package ru.parog.magacourseservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.parog.onlinelearningplatformmodel.entity.BaseEntity;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = Enrollment.TABLE_NAME)
public class Enrollment extends BaseEntity {
    public static final String TABLE_NAME = "enrollments";

    static class ColumnNames {
        public static final String ENROLLMENT_CN_USER_ID = "user_id";
        public static final String ENROLLMENT_CN_COURSE_ID = "course_id";
        public static final String ENROLLMENT_CN_ENROLLED_AT = "enrolled_at";
        public static final String ENROLLMENT_CN_COMPLETED_AT = "completed_at";
        public static final String ENROLLMENT_CN_COMPLETED = "completed";
    }

    @Column(name = ColumnNames.ENROLLMENT_CN_USER_ID, nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = ColumnNames.ENROLLMENT_CN_ENROLLED_AT)
    private LocalDateTime enrolledAt;

    @Column(name = ColumnNames.ENROLLMENT_CN_COMPLETED_AT)
    private LocalDateTime completedAt;

    @Column(name = ColumnNames.ENROLLMENT_CN_COMPLETED, nullable = false)
    private boolean completed;

}

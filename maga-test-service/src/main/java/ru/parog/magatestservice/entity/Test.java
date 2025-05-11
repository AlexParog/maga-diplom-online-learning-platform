package ru.parog.magatestservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.parog.onlinelearningplatformmodel.entity.BaseEntity;

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
@Table(name = Test.TABLE_NAME)
public class Test extends BaseEntity {

    public static final String TABLE_NAME = "tests";

    static class ColumnNames {
        public static final String TEST_CN_TITLE = "title";
        public static final String TEST_CN_DESCRIPTION = "description";
        public static final String TEST_CN_COURSE_ID = "course_id";
        public static final String TEST_CN_LESSON_ID = "lesson_id";
        public static final String TEST_CN_TIME_LIMIT = "time_limit";
    }

    @Column(name = ColumnNames.TEST_CN_TITLE, nullable = false)
    private String title;

    @Column(name = ColumnNames.TEST_CN_DESCRIPTION)
    private String description;

    @Column(name = ColumnNames.TEST_CN_TIME_LIMIT)
    private int timeLimit;

    @Column(name = ColumnNames.TEST_CN_COURSE_ID)
    private Long courseId;

    @Column(name = ColumnNames.TEST_CN_LESSON_ID)
    private Long lessonId;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();
}

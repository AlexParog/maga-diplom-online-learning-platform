package ru.parog.magacourseservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.parog.onlinelearningplatformmodel.entity.BaseEntity;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = Lesson.TABLE_NAME)
public class Lesson extends BaseEntity {

    public static final String TABLE_NAME = "lessons";

    static class ColumnNames {
        public static final String LESSON_CN_TITLE = "title";
        public static final String LESSON_CN_CONTENT = "content";
        public static final String LESSON_CN_ORDER_INDEX = "order_index";
    }

    @Column(name = ColumnNames.LESSON_CN_TITLE, nullable = false)
    private String title;

    @Column(name = ColumnNames.LESSON_CN_CONTENT, columnDefinition = "TEXT")
    private String content;

    @Column(name = ColumnNames.LESSON_CN_ORDER_INDEX, nullable = false)
    private Integer orderIndex;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    // Если урок содержит тест, храним ссылку на него
    private Long testId;
}

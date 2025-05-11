package ru.parog.magacourseservice.entity;

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
@Table(name = Course.TABLE_NAME)
public class Course extends BaseEntity {

    public static final String TABLE_NAME = "courses";

    static class ColumnNames {
        public static final String COURSE_CN_TITLE = "title";
        public static final String COURSE_CN_DESCRIPTION = "description";
        public static final String COURSE_CN_INSTRUCTOR_ID = "instructor_id";
        public static final String COURSE_CN_PUBLISHED = "published";
    }

    @Column(name = ColumnNames.COURSE_CN_TITLE, nullable = false)
    private String title;

    @Column(name = ColumnNames.COURSE_CN_DESCRIPTION, columnDefinition = "TEXT")
    private String description;

    @Column(name = ColumnNames.COURSE_CN_INSTRUCTOR_ID, nullable = false)
    private Long instructorId; // ссылка на User.id инструктора

    @Column(name = ColumnNames.COURSE_CN_PUBLISHED, nullable = false)
    private boolean published;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> modules = new ArrayList<>();
}

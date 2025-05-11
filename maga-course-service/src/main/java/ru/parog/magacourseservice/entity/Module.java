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
@Table(name = Module.TABLE_NAME)
public class Module extends BaseEntity {

    public static final String TABLE_NAME = "modules";

    static class ColumnNames {
        public static final String MODULE_CN_TITLE = "title";
        public static final String MODULE_CN_DESCRIPTION = "description";
        public static final String MODULE_CN_ORDER_INDEX = "order_index";
    }

    @Column(name = ColumnNames.MODULE_CN_TITLE, nullable = false)
    private String title;

    @Column(name = ColumnNames.MODULE_CN_DESCRIPTION, columnDefinition = "TEXT")
    private String description;

    // порядок в курсе, дефолтное значение 0
    @Column(name = ColumnNames.MODULE_CN_ORDER_INDEX, nullable = false)
    private Integer orderIndex;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();

}

package ru.parog.magatestservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.parog.magatestservice.entity.enums.QuestionType;
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
@Table(name = Question.TABLE_NAME)
public class Question extends BaseEntity {

    public static final String TABLE_NAME = "questions";

    static class ColumnNames {
        public static final String QUESTION_CN_TEXT = "question_text";
        public static final String QUESTION_CN_TYPE = "question_type";
        public static final String QUESTION_CN_POINTS = "points";
        public static final String QUESTION_CN_ORDER_INDEX = "order_index";
    }

    @Column(name = ColumnNames.QUESTION_CN_TEXT, nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(name = ColumnNames.QUESTION_CN_TYPE, nullable = false)
    private QuestionType questionType;

    @Column(name = ColumnNames.QUESTION_CN_POINTS, nullable = false)
    private int points;

    @Column(name = ColumnNames.QUESTION_CN_ORDER_INDEX)
    private Integer orderIndex;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerOption> options = new ArrayList<>();
}

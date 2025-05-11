package ru.parog.magatestservice.entity;

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
@Table(name = AnswerOption.TABLE_NAME)
public class AnswerOption extends BaseEntity {

    public static final String TABLE_NAME = "answer_options";

    static class ColumnNames {
        public static final String ANSWER_OPTION_CN_OPTION_TEXT = "option_text";
        public static final String ANSWER_OPTION_CN_IS_CORRECT = "is_correct";
    }

    @Column(name = ColumnNames.ANSWER_OPTION_CN_OPTION_TEXT, nullable = false, columnDefinition = "TEXT")
    private String optionText;

    @Column(name = ColumnNames.ANSWER_OPTION_CN_IS_CORRECT, nullable = false)
    private Boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}

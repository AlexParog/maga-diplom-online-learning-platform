package ru.parog.magasubmissionservice.entity;

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
@Table(name = SubmissionDetail.TABLE_NAME)
public class SubmissionDetail extends BaseEntity {

    public static final String TABLE_NAME = "submission_details";

    static class ColumnNames {
        public static final String SUBMISSION_DETAIL_CN_QUESTION_ID = "question_id";
        public static final String SUBMISSION_DETAIL_CN_TEXT_ANSWER = "text_answer";
        public static final String SUBMISSION_DETAIL_CN_CORRECT = "correct";
        public static final String SUBMISSION_DETAIL_CN_SCORE_EARNED = "score_earned";
    }

    @ManyToOne
    @JoinColumn(name = "submission_id", nullable = false)
    private Submission submission;

    @Column(name = ColumnNames.SUBMISSION_DETAIL_CN_QUESTION_ID, nullable = false)
    private Long questionId;

    @ElementCollection
    @CollectionTable(
            name = "submission_answers",
            joinColumns = @JoinColumn(name = "submission_detail_id")
    )
    @Column(name = "answer_option_id")
    private List<Long> selectedOptionIds = new ArrayList<>();

    @Column(name = ColumnNames.SUBMISSION_DETAIL_CN_TEXT_ANSWER)
    private String textAnswer;

    @Column(name = ColumnNames.SUBMISSION_DETAIL_CN_CORRECT, nullable = false)
    private boolean correct;

    @Column(name = ColumnNames.SUBMISSION_DETAIL_CN_SCORE_EARNED, nullable = false)
    private int scoreEarned;
}

package com.teamdefinex.dfxsurvey.domain.admin;

import com.teamdefinex.dfxsurvey.domain.base.BaseEntity;
import com.teamdefinex.dfxsurvey.domain.survey.Survey;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Questions extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @Enumerated(EnumType.STRING)
    private QuestionType type;
    private LocalDateTime expiresAt;
    private String question;
    private Integer questionOrder;
    private Boolean required = Boolean.FALSE;

    @ElementCollection
    @CollectionTable(
            name = "question_options",
            joinColumns = @JoinColumn(name = "question_id")
    )
    @Column(name = "option_value")
    private List<String> options = new ArrayList<>();

    public Questions duplicate() {
        Questions copy = new Questions();

        copy.setSurvey(this.survey);
        copy.setType(this.type);
        copy.setExpiresAt(this.expiresAt);
        copy.setQuestion(this.question);
        copy.setQuestionOrder(this.questionOrder);
        copy.setOptions(this.options);
        copy.setRequired(this.required);

        return copy;
    }
}
